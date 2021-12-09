package com.semihbkgr.gorun.view.highlight;

import android.content.Context;
import android.text.style.CharacterStyle;
import android.text.style.TextAppearanceSpan;

public class SpanUnit {

    public final CharacterStyle characterStyle;
    public final int start;
    public final int end;

    private SpanUnit(CharacterStyle characterStyle, int start, int end) {
        this.characterStyle = characterStyle;
        this.start = start;
        this.end = end;
    }

    public static SpanUnit of(Context context, HighlightUnit highlightUnit, int start, int end) {
        return new SpanUnit(new TextAppearanceSpan(context, highlightUnit.style), start, end);
    }

}
