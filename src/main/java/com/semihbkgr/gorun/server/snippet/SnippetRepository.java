package com.semihbkgr.gorun.server.snippet;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface SnippetRepository extends R2dbcRepository<Snippet, Integer> {

    Flux<SnippetInfo> findAllBy();

}
