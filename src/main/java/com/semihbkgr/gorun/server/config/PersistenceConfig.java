package com.semihbkgr.gorun.server.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.Optional;

@Configuration
@Slf4j
public class PersistenceConfig {

    @Bean
    @Profile("dev")
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory,
                                                    @Value("${sql.init.enabled:true}") boolean enabled,
                                                    @Value("${sql.init.ddl.filepath}") Optional<String> ddlFilepathOpt,
                                                    @Value("${sql.init.dml.filepath:data.sql}") Optional<String> dmlFilepathOpt) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setEnabled(enabled);
        log.info("SQL init enabled: {}", enabled);
        var populator = new CompositeDatabasePopulator();
        if (ddlFilepathOpt.isPresent()) {
            var ddlPopulator = new ResourceDatabasePopulator(new ClassPathResource(ddlFilepathOpt.get()));
            populator.addPopulators(ddlPopulator);
            log.info("DDL filepath: {}", ddlFilepathOpt.get());
        } else
            log.info("No DDL filepath defined");
        if (dmlFilepathOpt.isPresent()) {
            var dmlPopulator = new ResourceDatabasePopulator(new ClassPathResource(dmlFilepathOpt.get()));
            populator.addPopulators(dmlPopulator);
            log.info("DML filepath: {}", dmlFilepathOpt.get());
        } else
            log.info("No DML filepath defined");
        initializer.setDatabasePopulator(populator);
        initializer.setEnabled(enabled);
        return initializer;
    }

}
