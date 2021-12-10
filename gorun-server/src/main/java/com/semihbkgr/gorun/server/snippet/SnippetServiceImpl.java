package com.semihbkgr.gorun.server.snippet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;

    @Override
    public Mono<Snippet> find(int id) {
        return snippetRepository.findById(id);
    }

    @Override
    public Flux<SnippetInfo> findAllInfo() {
        return snippetRepository.findAllBy();
    }

}
