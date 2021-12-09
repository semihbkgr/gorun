package com.semihbkgr.gorun.server.snippet;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class InMemorySnippetHoldingStrategyTest {

    static final List<Snippet> snippetList = new ArrayList<>();

    final InMemorySnippetHoldingStrategy inMemorySnippetHoldingStrategy = new InMemorySnippetHoldingStrategy();

    @BeforeAll
    static void createSnippets() {
        snippetList.add(new Snippet(1, 1, -1, "title1", "brief1", "explanation1", "code1"));
        snippetList.add(new Snippet(2, 1, -1, "title2", "brief2", "explanation2", "code2"));
        snippetList.add(new Snippet(3, 1, -1, "title3", "brief3", "explanation3", "code3"));
    }

    @BeforeEach
    void putSnippets() {
        snippetList.forEach(snippet -> inMemorySnippetHoldingStrategy.getSnippetMap().put(snippet.getId(), snippet));
    }

    @Test
    void findAll() {
        var allSnippetInfoFlux = inMemorySnippetHoldingStrategy.findAllInfo().log();
        StepVerifier.create(allSnippetInfoFlux)
                .expectNext(SnippetInfo.of(snippetList.get(0)))
                .expectNext(SnippetInfo.of(snippetList.get(1)))
                .expectNext(SnippetInfo.of(snippetList.get(2)))
                .verifyComplete();

    }

    @Test
    void findById() {
        snippetList.forEach(snippet -> {
            var snippetMono = inMemorySnippetHoldingStrategy.findById(snippet.getId()).log();
            StepVerifier.create(snippetMono)
                    .expectNext(snippet)
                    .verifyComplete();
        });
    }

}