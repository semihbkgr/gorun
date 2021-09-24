package com.semihbkgr.gorun.server.snippet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetInfo {

    private int id;
    private String title;
    private String brief;

    public static SnippetInfo of(Snippet snippet) {
        return new SnippetInfo(snippet.getId(), snippet.getTitle(), snippet.getBrief());
    }

}
