package com.semihbkgr.gorun.server.snippet;

import org.springframework.stereotype.Component;

@Component
public class SnippetHolder {

    private Snippet[] snippets;

    public Snippet[] getSnippets() {
        return snippets;
    }

    public void setSnippets(Snippet[] snippets) {
        this.snippets = snippets;
    }

}
