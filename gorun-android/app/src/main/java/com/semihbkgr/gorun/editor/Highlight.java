package com.semihbkgr.gorun.editor;

import com.semihbkgr.gorun.R;

public enum Highlight {

    STRING(new String[]{"\""}, new String[]{"\""},R.style.StringStyle),
    FUNCTION( new String[]{"."},  new String[]{"("},R.style.FunctionStyle),
    CUSTOM_FUNCTION( new String[]{"func "},  new String[]{"("},R.style.CustomFunctionStyle),
    FUNC( new String[]{"func"},null, R.style.FuncStyle),
    IMPORT( new String[]{"import"},null,R.style.ImportStyle),
    PACKAGE( new String[]{"fmt","time","io","log","net"},null,R.style.PackageStyle),
    RETURN( new String[]{"return"},null,R.style.ReturnStyle),
    VAR( new String[]{"var"},null,R.style.ReturnStyle),
    TYPE(new String[]{"int,float","string","bool"},null,R.style.TypeStyle);

    public final String[] startWords;
    public final String[] endWords;
    public final int style;

    Highlight(String[] startWords, String[] endWords, int style) {
        this.startWords = startWords;
        this.endWords = endWords;
        this.style=style;
    }

    public boolean isOnly(){
        return endWords ==null;
    }

}
