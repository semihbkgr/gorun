package com.semihbg.gorun.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @GetMapping
    public Mono<String> asd() {
        return Mono.just("package main\r\n" +
                "import \"fmt\"\r\n" +
                "func main() {\r\n" +
                "    fmt.Println(\"hello world\")\r\n" +
                "}");
    }

}
