package com.semihbg.gorun.server;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final CodeRunService codeRunService;

    @PostMapping("/run")
    public Flux<String> run(@RequestBody String code) {
        return codeRunService.run(code);
    }

    @GetMapping
    public Mono<String> get() {
        return Mono.just("package main\n" +
                "import \"fmt\"\n" +
                "func main() {\n" +
                "    for i := 1;  i<=5; i++ {\n" +
                "        fmt.Printf(\"Welcome %d times\\n\",i)\n" +
                "    }\n" +
                "}");
    }

}
