package com.semihbkgr.gorun.view.highlight;

import java.util.List;

public abstract class UnitBasedHighlighter implements CodeHighlighter {

    protected final List<? extends HighlightUnit> highlightUnitList;

    protected UnitBasedHighlighter(List<? extends HighlightUnit> highlightUnitList) {
        this.highlightUnitList = highlightUnitList;
    }

}
