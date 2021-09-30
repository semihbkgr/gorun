package com.semihbkgr.gorun.snippet;

public class SnippetInfo {

    public final int id;
    public final int versionId;
    public final int order;
    public final String title;
    public final String brief;

    public SnippetInfo(int id, int versionId, int order, String title, String brief) {
        this.id = id;
        this.versionId = versionId;
        this.order = order;
        this.title = title;
        this.brief = brief;
    }

}
