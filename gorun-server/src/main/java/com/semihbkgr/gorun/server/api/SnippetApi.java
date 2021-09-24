package com.semihbkgr.gorun.server.api;

import com.semihbkgr.gorun.server.snippet.Snippet;
import com.semihbkgr.gorun.server.snippet.SnippetHolder;
import com.semihbkgr.gorun.server.snippet.SnippetInfo;
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

    private final SnippetHolder snippetHolder;

    @GetMapping
    public Flux<SnippetInfo> allSnippetsInfo() {
        return snippetHolder.findAllInfo();
    }

    @GetMapping("/{id}")
    public Mono<Snippet> getSnippet(@PathVariable("id")int id){
        return snippetHolder.findById(id);
    }

}
