package com.semihbg.gorun.server.api;


import com.semihbg.gorun.server.snippet.Snippet;
import com.semihbg.gorun.server.snippet.SnippetHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/snippet")
public class SnippetApi {

    private final SnippetHolder snippetHolder;

    @GetMapping
    public Flux<Snippet> snippets() {
        return Flux.just(snippetHolder.getSnippets());
    }

}
