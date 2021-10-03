package com.semihbkgr.gorun.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class ServerApplication {

    public static final String SNIPPETS_JSON_FILE_NAME = "snippets.json";

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
