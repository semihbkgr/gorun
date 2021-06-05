package com.semihbg.gorun.snippet;

import android.content.Context;

public abstract class SnippetAbstractRepository implements SnippetRepository {

    protected final Context context;

    public SnippetAbstractRepository(Context context) {
        this.context = context;
    }

}
