package com.semihbkgr.gorun.server.snippet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetBase {

    private int id;
    private String title;
    private String brief;

    public static SnippetBase of(Snippet snippet) {
        return new SnippetBase(snippet.getId(), snippet.getTitle(), snippet.getBrief());
    }

}
