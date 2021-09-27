package com.semihbkgr.gorun.snippet;

import com.semihbkgr.gorun.util.http.RequestException;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SnippetServiceImpl implements SnippetService {

    private final SnippetClient snippetClient;

    public SnippetServiceImpl(SnippetClient snippetClient) {
        this.snippetClient = snippetClient;
    }

    @Override
    public List<SnippetInfo> getAllSnippetInfos() throws RequestException {
        return Arrays.asList(snippetClient.getAllSnippetInfos());
    }

    @Override
    public void getAllSnippetInfosAsync(ResponseCallback<? super List<SnippetInfo>> callback) {
        snippetClient.getAllSnippetInfosAsync(callback.convertResponseType(Arrays::asList));
    }

    @Override
    public Future<List<SnippetInfo>> getAllSnippetInfosFuture() {
        return ((CompletableFuture<SnippetInfo[]>) snippetClient.getAllSnippetInfoFuture())
                .thenApplyAsync(Arrays::asList);
    }

    @Override
    public Snippet getSnippet(int id) throws RequestException {
        return snippetClient.getSnippet(id);
    }

    @Override
    public void getSnippetAsync(int id, ResponseCallback<? super Snippet> callback) {
        snippetClient.getSnippetAsync(id, callback);
    }

    @Override
    public Future<Snippet> getSnippetFuture(int id) {
        return snippetClient.getSnippetFuture(id);
    }

}
