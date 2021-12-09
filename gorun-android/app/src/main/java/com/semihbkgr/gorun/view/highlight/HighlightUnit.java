package com.semihbkgr.gorun.view.highlight;

import java.util.Objects;

public class HighlightUnit {

    public final String[] strings;
    public final boolean isIntermittent;
    public final int style;

    public HighlightUnit(String[] strings, boolean isIntermittent, int style) {
        this.strings = Objects.requireNonNull(strings);
        this.isIntermittent = isIntermittent;
        this.style = style;
    }

}
