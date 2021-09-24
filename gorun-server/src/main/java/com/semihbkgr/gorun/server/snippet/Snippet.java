package com.semihbkgr.gorun.server.snippet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Snippet extends SnippetBase {

    private String explanation;
    private String code;

}
