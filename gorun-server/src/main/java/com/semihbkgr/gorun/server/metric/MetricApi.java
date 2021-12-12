package com.semihbkgr.gorun.server.metric;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class MetricApi {

    private final ServerInfoManager serverInfoManager;

    @GetMapping
    public Mono<ServerInfoModel> serverInfo() {
        return Mono.just(
                ServerInfoModel.builder()
                        .sessionCount(serverInfoManager.getSessionCount())
                        .executionCount(serverInfoManager.getExecutionCount())
                        .currentExecutionCount(serverInfoManager.getCurrentExecutionCount())
                        .build()
        );
    }

    @GetMapping("/session-count")
    public Mono<Integer> sessionCount() {
        return Mono.just(serverInfoManager.getSessionCount());
    }

    @GetMapping("/execution-count")
    public Mono<Integer> executionCount() {
        return Mono.just(serverInfoManager.getExecutionCount());
    }

    @GetMapping("/current-execution-count")
    public Mono<Integer> currentExecutionCount() {
        return Mono.just(serverInfoManager.getCurrentExecutionCount());
    }

    @Data
    @Builder
    private static class ServerInfoModel {
        private int sessionCount;
        private int currentExecutionCount;
        private int executionCount;
    }

}
