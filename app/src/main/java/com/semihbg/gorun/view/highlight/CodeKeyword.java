package com.semihbg.gorun.view.highlight;

import android.graphics.Color;

public enum CodeKeyword {

    FUNC("func",Color.GRAY),
    IMPORT("import",Color.RED),
    FMT("fmt",Color.WHITE),
    RETURN("return",Color.BLUE);

    public final String code;
    public final int color;

    CodeKeyword(String code, int color) {
        this.code = code;
        this.color = color;
    }

}
