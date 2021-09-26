package com.semihbkgr.gorun.snippet;

import java.util.List;
import java.util.function.Consumer;

public interface SnippetService {

    List<SnippetInfo> getAllSnippetInfo();

    void getAllSnippetInfoAsync(Consumer<? super List<SnippetInfo>> callback);

    Snippet getSnippet(int id);

    void getSnippetAsync(int id,Consumer<? super List<SnippetInfo>> callback);

}
