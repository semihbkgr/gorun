package com.semihbg.gorun.view.code.highlight;

import com.semihbg.gorun.R;

public enum Highlight {

    STRING("\"","\"",R.style.StringStyle),
    FUNCTION(".","(",R.style.FunctionStyle),
    CUSTOM_FUNCTION("func ","(",R.style.CustomFunctionStyle),
    FUNC("func",null, R.style.FuncStyle),
    IMPORT("import",null,R.style.ImportStyle),
    FMT("fmt",null,R.style.PackageStyle),
    RETURN("return",null,R.style.ReturnStyle);


    public final String startWord;
    public final String endWord;
    public final int style;

    Highlight(String startWord, String endWord, int style) {
        this.startWord = startWord;
        this.endWord = endWord;
        this.style=style;
    }

    public boolean isOnly(){
        return endWord ==null;
    }

    public boolean isBetween(){
        return endWord !=null;
    }

}
