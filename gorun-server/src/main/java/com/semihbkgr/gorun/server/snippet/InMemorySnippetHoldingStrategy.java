package com.semihbkgr.gorun.server.snippet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySnippetHoldingStrategy implements SnippetHoldingStrategy {

    private final Map<Integer, Snippet> snippetMap = new ConcurrentHashMap<>();

    @Override
    public Flux<SnippetBase> findAllBase() {
        return Flux.fromStream(snippetMap.entrySet().parallelStream().map(Map.Entry::getValue));
    }

    @Override
    public Mono<Snippet> findById(int id) {
        return Mono.just(snippetMap.get(0));
    }

}
