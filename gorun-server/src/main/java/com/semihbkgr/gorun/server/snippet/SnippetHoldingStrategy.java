package com.semihbkgr.gorun.server.snippet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SnippetHoldingStrategy {

    Flux<SnippetBase> findAllBase();

    Mono<Snippet> findById(int id);

}
