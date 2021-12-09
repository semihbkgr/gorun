package com.semihbkgr.gorun.server.config;

import com.semihbkgr.gorun.server.run.RunProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class RunConfig {

    @Bean
    @ConfigurationProperties("run")
    public RunProperties runProperties() {
        return new RunProperties();
    }

}
