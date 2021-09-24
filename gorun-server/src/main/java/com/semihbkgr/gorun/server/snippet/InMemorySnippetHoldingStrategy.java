package com.semihbkgr.gorun.server.snippet;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySnippetHoldingStrategy implements SnippetHoldingStrategy {

    private final Map<Integer, Snippet> snippetMap = new ConcurrentHashMap<>();

    @Override
    public Flux<SnippetInfo> findAllInfo() {
        return Flux.fromStream(
                snippetMap.entrySet()
                        .parallelStream()
                        .map(entry -> SnippetInfo.of(entry.getValue()))
        );
    }

    @Override
    public Mono<Snippet> findById(int id) {
        return Mono.just(snippetMap.get(id));
    }

    public Map<Integer, Snippet> getSnippetMap() {
        return snippetMap;
    }

}
