package com.semihbkgr.gorun.editor;

import android.content.Context;
import android.text.style.CharacterStyle;
import android.text.style.TextAppearanceSpan;

public class CodeHighlightContext {

    private final CharacterStyle characterStyle;
    private final int start;
    private final int end;

    private CodeHighlightContext(CharacterStyle characterStyle, int start, int end) {
        this.characterStyle = characterStyle;
        this.start = start;
        this.end = end;
    }

    public static CodeHighlightContext of(Context context, HighlightUnits highlightUnits, int start, int end){
        return new CodeHighlightContext(new TextAppearanceSpan(context, highlightUnits.style),start,end);
    }

    public CharacterStyle getCharacterStyle() {
        return characterStyle;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
