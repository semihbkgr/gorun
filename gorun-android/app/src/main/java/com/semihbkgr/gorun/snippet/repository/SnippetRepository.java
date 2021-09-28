package com.semihbkgr.gorun.snippet.repository;

import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;

import java.util.List;

public interface SnippetRepository {

    List<SnippetInfo> getSnippetInfos();

    Snippet getSnippet(int id);

    void saveSnippet(Snippet snippet);

    void updateSnippet(int id,Snippet snippet);

    void deleteSnippet(int id);

}
