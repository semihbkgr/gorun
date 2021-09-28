package com.semihbkgr.gorun.server.snippet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class InMemorySnippetHoldingStrategy implements SnippetHoldingStrategy {

    private final Map<Integer, Snippet> snippetMap = new ConcurrentHashMap<>();

    @Override
    public Flux<SnippetInfo> findAllInfo() {
        InheritableThreadLocal
        return Flux.fromStream(
                snippetMap.entrySet()
                        .parallelStream()
                        .map(entry -> SnippetInfo.of(entry.getValue()))
        );
    }

    @Override
    public Mono<Snippet> findById(int id) {
        return Mono.defer(() -> {
            if (snippetMap.containsKey(id))
                return Mono.just(snippetMap.get(id));
            else
                return Mono.empty();
        });
    }

    public Map<Integer, Snippet> getSnippetMap() {
        return snippetMap;
    }

}
