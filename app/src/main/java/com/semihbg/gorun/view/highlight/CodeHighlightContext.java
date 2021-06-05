package com.semihbg.gorun.view.highlight;

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

    public static CodeHighlightContext of(Context context, CodeKeyword codeKeyword, int start){
        return new CodeHighlightContext(new TextAppearanceSpan(context, codeKeyword.style),start, start+codeKeyword.keyword.length());
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
