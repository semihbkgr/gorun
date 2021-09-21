package com.semihbg.gorun.snippet;

import android.os.Handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

public class DefaultSnippetService implements SnippetService {

    private final SnippetCache snippetCache;
    private final SnippetClient snippetClient;

    public DefaultSnippetService(SnippetClient snippetClient) {
        this.snippetClient = snippetClient;
        this.snippetCache = new ListSnippetCache();
    }

    @Override
    public List<Snippet> getSnippets() {
        if (isAvailable())
            return snippetCache.getCache();
        List<Snippet> snippetList= Arrays.asList(snippetClient.getSnippetsBlock());
        snippetCache.setCache(snippetList);
        return snippetList;
    }

    @Override
    public void getSnippetsAsync(Consumer<? super List<Snippet>> snippetListConsumer) {
        if (isAvailable()) ForkJoinPool.commonPool().execute((() -> snippetListConsumer.accept(snippetCache.getCache())));
        else snippetClient.getSnippetAsync(snippets -> {
                List<Snippet> snippetList=snippets!=null?Arrays.asList(snippets): Collections.emptyList();
                snippetCache.setCache(snippetList);
                snippetListConsumer.accept(snippetList);
            });
    }

    @Override
    public boolean isAvailable() {
        return snippetCache.isCached();
    }

}
