package com.semihbkgr.gorun.server.api;

import com.semihbkgr.gorun.server.snippet.Snippet;
import com.semihbkgr.gorun.server.snippet.SnippetHolder;
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



}
