package com.semihbkgr.gorun.server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MainApi {

    @GetMapping("/status")
    public Mono<String> serverStatus(){
        return Mono.just("UP");
    }

}
