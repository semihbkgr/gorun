package com.semihbkgr.gorun.server.api;

import com.semihbkgr.gorun.server.component.ServerInfoManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoApi {

    private ServerInfoManager serverInfoManager;

    @GetMapping("/session-count")
    public Mono<Integer> sessionCount() {
        return Mono.just(serverInfoManager.getSessionCount());
    }


    @GetMapping("/execution-count")
    public Mono<Integer> executionCount() {
        return Mono.just(serverInfoManager.getExecutionCount());
    }

}
