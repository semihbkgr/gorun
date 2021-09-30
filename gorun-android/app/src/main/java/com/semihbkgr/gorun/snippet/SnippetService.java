package com.semihbkgr.gorun.snippet;

import com.semihbkgr.gorun.util.http.RequestException;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.List;
import java.util.Optional;

public interface SnippetService {

    List<SnippetInfo> getAllSnippetInfos() throws RequestException;

    void getAllSnippetInfosAsync(ResponseCallback<? super List<SnippetInfo>> callback);

    Snippet getSnippet(int id) throws RequestException;

    void getSnippetAsync(int id, ResponseCallback<? super Snippet> callback);

    List<SnippetInfo> getAllSavedSnippetInfos();

    Optional<Snippet> getSavedSnippet(int id);

    void save(Snippet snippet);

    void update(Snippet snippet);

}
