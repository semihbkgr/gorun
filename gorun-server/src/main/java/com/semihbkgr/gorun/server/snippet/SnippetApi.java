package com.semihbkgr.gorun.server.snippet;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/snippet")
public class SnippetApi {

    private final SnippetService snippetService;

    @GetMapping
    public Flux<SnippetInfo> allSnippetsInfo() {
        return snippetService.findAllInfo();
    }

    @GetMapping("/{id}")
    public Mono<Snippet> getSnippet(@PathVariable("id") int id) {
        return snippetService.find(id);
    }

}
