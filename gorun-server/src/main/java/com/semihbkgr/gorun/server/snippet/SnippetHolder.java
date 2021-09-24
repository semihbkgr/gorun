package com.semihbkgr.gorun.server.snippet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SnippetHolder {

    private final SnippetHoldingStrategy snippetHoldingStrategy;

    public Flux<SnippetInfo> findAllBase() {
        return snippetHoldingStrategy.findAllInfo();
    }

    public Mono<Snippet> findById(int id) {
        return snippetHoldingStrategy.findById(id);
    }

}
