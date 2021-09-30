package com.semihbkgr.gorun.snippet;

import androidx.annotation.NonNull;
import com.semihbkgr.gorun.cache.TimedCacheService;
import com.semihbkgr.gorun.snippet.client.SnippetClient;
import com.semihbkgr.gorun.snippet.repository.SnippetRepository;
import com.semihbkgr.gorun.util.http.RequestException;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.Optional;

public class CacheableSnippetService extends SnippetServiceImpl {

    private final TimedCacheService<Integer, Snippet> snippetTimedCacheService;

    public CacheableSnippetService(@NonNull SnippetClient snippetClient,
                                   @NonNull SnippetRepository snippetRepository,
                                   @NonNull TimedCacheService<Integer, Snippet> snippetTimedCacheService) {
        super(snippetClient, snippetRepository);
        this.snippetTimedCacheService = snippetTimedCacheService;
    }

    @Override
    public Snippet getSnippet(int id) throws RequestException {
        Optional<Snippet> snippetOpt = snippetTimedCacheService.get(id);
        if (!snippetOpt.isPresent()) {
            Snippet snippet = super.getSnippet(id);
            snippetTimedCacheService.cache(id, snippet);
            return snippet;
        } else
            return snippetOpt.get();
    }

    @Override
    public Optional<Snippet> getSavedSnippet(int id) {
        Optional<Snippet> cachedSnippetOpt = snippetTimedCacheService.get(id);
        if (!cachedSnippetOpt.isPresent()) {
            Optional<Snippet> savedSnippet = super.getSavedSnippet(id);
            savedSnippet.ifPresent(snippet -> snippetTimedCacheService.cache(id, snippet));
            return savedSnippet;
        } else
            return cachedSnippetOpt;
    }

    @Override
    public void getSnippetAsync(int id, ResponseCallback<? super Snippet> callback) {
        Optional<Snippet> cachedSnippetOpt = snippetTimedCacheService.get(id);
        if (!cachedSnippetOpt.isPresent())
            super.getSnippetAsync(id, callback.after(snippet -> {
                if (snippet instanceof Snippet) {
                    Snippet s = (Snippet) snippet;
                    snippetTimedCacheService.cache(s.id, s);
                }
            }));
        else
            callback.onResponse(cachedSnippetOpt.get());
    }

}
