package com.semihbg.gorun.view.code.highlight;

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

    public static CodeHighlightContext ofOnly(Context context, Highlight highlight, int start){
        return new CodeHighlightContext(new TextAppearanceSpan(context, highlight.style),start, start+ highlight.startWord.length());
    }

    public static CodeHighlightContext ofBetween(Context context, Highlight highlight, int start, int end){
        return new CodeHighlightContext(new TextAppearanceSpan(context, highlight.style),start,end);
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
