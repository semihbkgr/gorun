package com.semihbkgr.gorun.snippet;

import com.semihbkgr.gorun.util.RequestException;
import com.semihbkgr.gorun.util.ResponseCallback;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface SnippetClient {

    SnippetInfo[] getAllSnippetInfos() throws RequestException;

    void getAllSnippetInfosAsync(ResponseCallback<? super SnippetInfo[]> callback);

    Future<SnippetInfo[]> getAllSnippetInfoFuture();

    Snippet getSnippet(int id) throws RequestException;

    void getSnippetAsync(int id,ResponseCallback<? super Snippet> callback);

    Future<Snippet> getSnippetFuture(int id);

}
