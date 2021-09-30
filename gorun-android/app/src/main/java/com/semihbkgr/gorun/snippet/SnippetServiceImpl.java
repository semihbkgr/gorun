package com.semihbkgr.gorun.snippet;

import androidx.annotation.NonNull;
import com.semihbkgr.gorun.snippet.client.SnippetClient;
import com.semihbkgr.gorun.snippet.repository.SnippetRepository;
import com.semihbkgr.gorun.util.http.RequestException;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SnippetServiceImpl implements SnippetService {

    protected final SnippetClient snippetClient;
    protected final SnippetRepository snippetRepository;

    public SnippetServiceImpl(@NonNull SnippetClient snippetClient, @NonNull SnippetRepository snippetRepository) {
        this.snippetClient = snippetClient;
        this.snippetRepository = snippetRepository;
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
    public Snippet getSnippet(int id) throws RequestException {
        return snippetClient.getSnippet(id);
    }

    @Override
    public void getSnippetAsync(int id, ResponseCallback<? super Snippet> callback) {
        snippetClient.getSnippetAsync(id, callback);
    }

    @Override
    public List<SnippetInfo> getAllSavedSnippetInfos() {
        return snippetRepository.findAll();
    }

    @Override
    public Optional<Snippet> getSavedSnippet(int id) {
        return Optional.ofNullable(snippetRepository.findById(id));
    }

}
