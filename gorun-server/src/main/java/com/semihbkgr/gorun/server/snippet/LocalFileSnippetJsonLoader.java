package com.semihbkgr.gorun.server.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LocalFileSnippetJsonLoader implements CommandLineRunner {

    private final InMemorySnippetHoldingStrategy inMemorySnippetHoldingStrategy;
    private final ObjectMapper objectMapper;
    private final String fileName;

    public LocalFileSnippetJsonLoader(InMemorySnippetHoldingStrategy inMemorySnippetHoldingStrategy,
                                      ObjectMapper objectMapper, @Value("${snippet.file-path:snippets.json}") String fileName) {
        this.inMemorySnippetHoldingStrategy = inMemorySnippetHoldingStrategy;
        this.objectMapper = objectMapper;
        this.fileName = fileName;
    }

    @Override
    public void run(String... args) throws Exception {
        byte[] bytes = new ClassPathResource(fileName)
                .getInputStream()
                .readAllBytes();
        String snippetsString = new String(bytes, StandardCharsets.UTF_8);
        Snippet[] snippets = objectMapper.readValue(snippetsString, Snippet[].class);
        log.info("Snippets have been loaded successfully, {} snippets found", snippets.length);
        for (var snippet : snippets)
            inMemorySnippetHoldingStrategy.getSnippetMap().put(snippet.getId(), snippet);
    }

}
