package com.semihbkgr.gorun.snippet;

import com.semihbkgr.gorun.util.excepiton.RequestException;
import com.semihbkgr.gorun.util.ResponseCallback;

import java.util.List;
import java.util.concurrent.Future;

public interface SnippetService {

    List<SnippetInfo> getAllSnippetInfos() throws RequestException;

    void getAllSnippetInfosAsync(ResponseCallback<? super List<SnippetInfo>> callback);

    Future<List<SnippetInfo>> getAllSnippetInfosFuture();

    Snippet getSnippet(int id) throws RequestException;

    void getSnippetAsync(int id, ResponseCallback<? super Snippet> callback);

    Future<Snippet> getSnippetFuture(int id);

}
