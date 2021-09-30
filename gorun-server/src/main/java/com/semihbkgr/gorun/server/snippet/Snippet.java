package com.semihbkgr.gorun.server.snippet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Snippet {

    private int id;
    private int versionId;
    private int order;
    private String title;
    private String brief;
    private String explanation;
    private String code;

}
