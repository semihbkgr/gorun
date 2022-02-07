package com.semihbkgr.gorun.server.snippet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SnippetService {

    Mono<Snippet> find(int id);

    Flux<SnippetInfo> findAllInfo();

}
