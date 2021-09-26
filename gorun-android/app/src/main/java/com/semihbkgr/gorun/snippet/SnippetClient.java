package com.semihbkgr.gorun.snippet;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface SnippetClient {

    SnippetInfo[] getAllSnippetInfos() throws IOException;

    void getAllSnippetInfosAsync(Consumer<? super SnippetInfo[]> callback);

    Future<SnippetInfo[]> getAllSnippetInfoFuture();

    Snippet getSnippet(int id) throws IOException;

    void getSnippetAsync(int id,Consumer<? super SnippetInfo> callback);

    Future<Snippet> getSnippetFuture(int id);

}
