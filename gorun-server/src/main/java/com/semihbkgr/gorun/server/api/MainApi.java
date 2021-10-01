package com.semihbkgr.gorun.server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping
public class MainApi {

    @GetMapping("status")
    public Mono<Boolean> serverStatus(){
        return Mono.just(true);
    }

}
