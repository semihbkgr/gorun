package com.semihbg.gorun.snippet;

import java.util.List;
import java.util.function.Consumer;

public interface SnippetService {

    List<Snippet> getSnippets();

    void getSnippetsAsync(Consumer<? super List<Snippet>> snippetListConsumer);

    boolean isAvailable();

}
