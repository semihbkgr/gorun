package com.semihbkgr.gorun.snippet;

import java.util.List;

public interface SnippetCache {

    List<Snippet> getCache();

    void setCache(List<Snippet> snippetList);

    boolean isCached();

}
