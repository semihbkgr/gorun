package com.semihbg.gorun.snippet;

import android.content.Context;

import java.util.List;

public abstract class SnippetServiceAbstract implements SnippetService {

    protected final Context context;

    public SnippetServiceAbstract(Context context) {
        this.context = context;
    }

}
