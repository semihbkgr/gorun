package com.semihbg.gorun.server;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final CodeRunService codeRunService;

    @GetMapping("/run")
    public Flux<String> run(@RequestBody String code) {
        return codeRunService.run(code);
    }

}
