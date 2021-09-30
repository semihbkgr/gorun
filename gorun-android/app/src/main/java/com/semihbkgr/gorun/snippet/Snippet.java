package com.semihbkgr.gorun.snippet;

public class Snippet {

    public final int id;
    public final String title;
    public final String brief;
    public final String explanation;
    public final String code;

    public Snippet(int id, String title, String brief, String explanation, String code) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.explanation = explanation;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", explanation='" + explanation + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snippet snippet = (Snippet) o;

        if (id != snippet.id) return false;
        if (title != null ? !title.equals(snippet.title) : snippet.title != null) return false;
        if (brief != null ? !brief.equals(snippet.brief) : snippet.brief != null) return false;
        if (explanation != null ? !explanation.equals(snippet.explanation) : snippet.explanation != null) return false;
        return code != null ? code.equals(snippet.code) : snippet.code == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (brief != null ? brief.hashCode() : 0);
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

}
