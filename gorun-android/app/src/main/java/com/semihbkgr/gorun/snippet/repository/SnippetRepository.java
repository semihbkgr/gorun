package com.semihbkgr.gorun.snippet.repository;

import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;

import java.util.List;

public interface SnippetRepository {

    List<SnippetInfo> findAll();

    List<Integer> findAllId();

    Snippet findById(int id);

    void save(Snippet snippet);

    void update(Snippet snippet);

    void deleteById(int id);

}
