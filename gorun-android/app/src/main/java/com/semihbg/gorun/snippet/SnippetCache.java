package com.semihbg.gorun.snippet;

import java.util.List;
import java.util.function.Consumer;

public interface SnippetCache {

    List<Snippet> getCache();

    void setCache(List<Snippet> snippetList);

    boolean isCached();

}
