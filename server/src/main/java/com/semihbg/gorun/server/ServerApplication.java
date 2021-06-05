package com.semihbg.gorun.server;

import com.semihbg.gorun.server.service.CodeRunLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ServerApplication {

    public static final String SNIPPETS_JSON_FILE_NAME="snippets.json";

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
