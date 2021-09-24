package com.semihbkgr.gorun.server.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihbkgr.gorun.server.ServerApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class SnippetStartupInterceptor implements CommandLineRunner {

    private final SnippetHolder snippetHolder;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        byte[] bytes=new ClassPathResource(ServerApplication.SNIPPETS_JSON_FILE_NAME)
                .getInputStream()
                .readAllBytes();
        String snippetsString=new String(bytes, StandardCharsets.UTF_8);
        Snippet[] snippets=objectMapper.readValue(snippetsString,Snippet[].class);
        log.info("Snippets has been read successfully, {} snippet found",snippets.length);

    }

}
