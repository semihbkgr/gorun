package com.semihbg.gorun.view.highlight;

import com.semihbg.gorun.R;

public enum CodeKeyword {

    FUNC("func", R.style.FuncStyle),
    IMPORT("import",R.style.ImportStyle),
    FMT("fmt",R.style.PackageStyle),
    RETURN("return",R.color.Return);

    public final String keyword;
    public final int style;

    CodeKeyword(String keyword, int style) {
        this.keyword = keyword;
        this.style=style;
    }

}
