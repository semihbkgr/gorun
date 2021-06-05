package com.semihbg.gorun.snippet;

import java.util.List;
import java.util.function.Consumer;

public interface SnippetRepository {

    List<Snippet> getAllSnippetsBlock();

    void getAllSnippetsAsync(Consumer<? super List<Snippet>> consumerSnippetList);

    boolean isCached();

}
