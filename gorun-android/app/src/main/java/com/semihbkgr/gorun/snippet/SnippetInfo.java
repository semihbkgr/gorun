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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnippetInfo that = (SnippetInfo) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return brief != null ? brief.equals(that.brief) : that.brief == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (brief != null ? brief.hashCode() : 0);
        return result;
    }

}
