package com.semihbg.gorun.server;

import com.semihbg.gorun.server.service.CodeRunLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    private final CodeRunLogService codeRunLogService;


    @GetMapping("/code/1")
    public Mono<String> code1() {
        StringBuilder code=new StringBuilder();
        code.append("<html>")
                .append("<body>")
                .append("")
                .append("</body>")
                .append("</html>")

        return Mono.just();
    }

    @GetMapping("/log")
    public Mono<String> log(){
        return Mono.just(codeRunLogService.getLogAsString());
    }

}
