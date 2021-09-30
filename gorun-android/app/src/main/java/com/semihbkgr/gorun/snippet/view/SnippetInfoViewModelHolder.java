package com.semihbkgr.gorun.snippet.view;

import androidx.annotation.NonNull;
import com.semihbkgr.gorun.snippet.SnippetInfo;

public class SnippetInfoViewModelHolder implements Comparable<SnippetInfoViewModelHolder> {

    public final SnippetInfo snippetInfo;
    private boolean downloaded;

    private SnippetInfoViewModelHolder(SnippetInfo snippetInfo, boolean downloaded) {
        this.snippetInfo = snippetInfo;
        this.downloaded = downloaded;
    }

    public static SnippetInfoViewModelHolder downloadedOf(@NonNull SnippetInfo snippetInfo){
        return new SnippetInfoViewModelHolder(snippetInfo,true);
    }

    public static SnippetInfoViewModelHolder nonDownloadedOf(@NonNull SnippetInfo snippetInfo){
        return new SnippetInfoViewModelHolder(snippetInfo,false);
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    @Override
    public int compareTo(SnippetInfoViewModelHolder o) {
        return snippetInfo.order-o.snippetInfo.order;
    }

}
