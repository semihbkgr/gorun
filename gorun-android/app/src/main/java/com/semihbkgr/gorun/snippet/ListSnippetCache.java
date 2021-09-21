package com.semihbkgr.gorun.snippet;

import android.util.Log;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSnippetCache implements SnippetCache {

    private static final String TAG = ListSnippetCache.class.getName();

    private List<Snippet> cacheList;
    private boolean isCached;

    public ListSnippetCache() {
        this.cacheList = new ArrayList<>();
        this.isCached = false;
    }

    @Override
    @Nullable
    public List<Snippet> getCache() {
        if (this.isCached) {
            Log.i(TAG, "getCache: Snippets data taken from cache");
            return Collections.unmodifiableList(this.cacheList);
        }
        return null;
    }

    @Override
    public void setCache(List<Snippet> snippetList) {
        this.cacheList.clear();
        this.cacheList.addAll(snippetList);
        Log.i(TAG, "setCache: Snippets data has been cached");
    }

    @Override
    public boolean isCached() {
        return this.isCached;
    }

}
