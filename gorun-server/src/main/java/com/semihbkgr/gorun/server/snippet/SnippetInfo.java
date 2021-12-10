package com.semihbkgr.gorun.server.snippet;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SnippetInfo {

    private int id;
    private int version;
    private int displayOrder;
    private String title;
    private String brief;

    public static SnippetInfo of(Snippet snippet) {
        return SnippetInfo.builder()
                .id(snippet.getId())
                .version(snippet.getVersion())
                .displayOrder(snippet.getDisplayOrder())
                .title(snippet.getTitle())
                .brief(snippet.getBrief())
                .build();
    }

}
