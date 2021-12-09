package com.semihbkgr.gorun.view.highlight;

import java.util.List;

public abstract class UnitBasedHighlighter implements CodeHighlighter {

    protected final List<HighlightUnit> highlightUnitList;

    protected UnitBasedHighlighter(List<HighlightUnit> highlightUnitList) {
        this.highlightUnitList = highlightUnitList;
    }

}
