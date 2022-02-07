package com.semihbkgr.gorun.server.snippet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetInfo {

    private int id;
    private int version;
    private int displayOrder;
    private String title;
    private String brief;

}
