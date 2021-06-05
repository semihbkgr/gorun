package com.semihbg.gorun.snippet;

import java.util.function.Consumer;

public interface SnippetClient {

    Snippet[] getSnippetsBlock();

    void getSnippetAsync(Consumer<? super Snippet[]> snippetArrayConsumer);

    String getSnippetAsJsonBlock();

    void getSnippetAsJsonAsync(Consumer<? super String> stringConsumer);

}
