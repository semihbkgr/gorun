package com.semihbkgr.gorun.snippet.view;

import androidx.annotation.NonNull;
import com.semihbkgr.gorun.snippet.SnippetInfo;

public class SnippetInfoViewModelHolder implements Comparable<SnippetInfoViewModelHolder> {

    public final SnippetInfo snippetInfo;
    public final boolean removeFromListWhenDeleted;
    private boolean downloaded;

    private SnippetInfoViewModelHolder(SnippetInfo snippetInfo, boolean downloaded, boolean removeFromListWhenDeleted) {
        this.snippetInfo = snippetInfo;
        this.downloaded = downloaded;
        this.removeFromListWhenDeleted = removeFromListWhenDeleted;
    }

    public static SnippetInfoViewModelHolder downloadedOf(@NonNull SnippetInfo snippetInfo, boolean removeFromListWhenDeleted) {
        return new SnippetInfoViewModelHolder(snippetInfo, true, removeFromListWhenDeleted);
    }

    public static SnippetInfoViewModelHolder nonDownloadedOf(@NonNull SnippetInfo snippetInfo, boolean removeFromListWhenDeleted) {
        return new SnippetInfoViewModelHolder(snippetInfo, false, removeFromListWhenDeleted);
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    @Override
    public int compareTo(SnippetInfoViewModelHolder o) {
        return snippetInfo.order - o.snippetInfo.order;
    }

}
