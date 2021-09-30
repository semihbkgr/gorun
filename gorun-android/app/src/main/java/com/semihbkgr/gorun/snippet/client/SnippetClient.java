package com.semihbkgr.gorun.snippet.client;

import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;
import com.semihbkgr.gorun.util.http.RequestException;
import com.semihbkgr.gorun.util.http.ResponseCallback;

public interface SnippetClient {

    SnippetInfo[] getAllSnippetInfos() throws RequestException;

    void getAllSnippetInfosAsync(ResponseCallback<? super SnippetInfo[]> callback);

    Snippet getSnippet(int id) throws RequestException;

    void getSnippetAsync(int id, ResponseCallback<? super Snippet> callback);

}
