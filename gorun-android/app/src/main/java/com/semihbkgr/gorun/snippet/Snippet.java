package com.semihbkgr.gorun.snippet;

public class Snippet {

    public final int id;
    public final int versionId;
    public final int order;
    public final String title;
    public final String brief;
    public final String explanation;
    public final String code;

    public Snippet(int id, int versionId, int order, String title, String brief, String explanation, String code) {
        this.id = id;
        this.versionId = versionId;
        this.order = order;
        this.title = title;
        this.brief = brief;
        this.explanation = explanation;
        this.code = code;
    }

}
