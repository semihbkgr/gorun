package com.semihbkgr.gorun.server.snippet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SnippetRepository {

    Flux<SnippetInfo> findAllInfo();

    Mono<Snippet> findById();

}
