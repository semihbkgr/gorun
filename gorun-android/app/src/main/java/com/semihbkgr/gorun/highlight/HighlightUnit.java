package com.semihbkgr.gorun.highlight;

import java.util.Objects;

public class HighlightUnit {

    public final String[] startSyntaxes;
    public final String[] endSyntaxes;
    public final int style;

    public HighlightUnit(String[] startSyntaxes, String[] endSyntaxes, int style) {
        this.startSyntaxes = Objects.requireNonNull(startSyntaxes);
        this.endSyntaxes = endSyntaxes;
        this.style = style;
    }

    public HighlightUnit(String[] startSyntaxes, int style) {
        this(startSyntaxes, null, style);
    }

    public boolean isIntermittent() {
        return endSyntaxes == null;
    }

}
