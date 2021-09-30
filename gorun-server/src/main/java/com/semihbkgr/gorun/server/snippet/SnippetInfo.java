package com.semihbkgr.gorun.server.snippet;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SnippetInfo {

    private int id;
    private int versionId;
    private int order;
    private String title;
    private String brief;

    public static SnippetInfo of(Snippet snippet) {
        return SnippetInfo.builder()
                .id(snippet.getId())
                .versionId(snippet.getVersionId())
                .order(snippet.getOrder())
                .title(snippet.getTitle())
                .brief(snippet.getBrief())
                .build();
    }

}
