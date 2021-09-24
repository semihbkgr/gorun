package com.semihbkgr.gorun.server.snippet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SnippetRepository {

    Flux<SnippetBase> findAllInfo();

    Mono<Snippet> findById();

}
