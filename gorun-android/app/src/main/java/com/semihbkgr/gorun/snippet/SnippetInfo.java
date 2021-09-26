package com.semihbkgr.gorun.snippet;

public class SnippetInfo {

    public final int id;
    public final String title;
    public final String brief;

    public SnippetInfo(int id, String title, String brief) {
        this.id = id;
        this.title = title;
        this.brief = brief;
    }

    @Override
    public String toString() {
        return "SnippetInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }
}
