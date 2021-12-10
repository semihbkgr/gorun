package com.semihbkgr.gorun.server.snippet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("snippets")
public class Snippet {

    @Id
    private int id;
    private int version;
    private int displayOrder;
    private String title;
    private String brief;
    private String explanation;
    private String code;

}
