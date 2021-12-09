package com.semihbkgr.gorun.view.highlight;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;

public class DefaultCodeHighlighter extends UnitBasedHighlighter {

    private final Context context;
    private final Handler handler;

    public DefaultCodeHighlighter(Context context, List<HighlightUnit> highlightUnitList) {
        super(highlightUnitList);
        this.context = context;
        this.handler = new Handler(context.getMainLooper());
    }

    @Override
    public void highlight(Editable editable) {
        String code = editable.toString();
        List<SpanUnit> spanUnitList = new ArrayList<>();
        for (HighlightUnit highlightUnit : highlightUnitList) {
            if (highlightUnit.isIntermittent)
                for (String startWord : highlightUnit.strings)
                    for (int i = code.indexOf(startWord); i > -1; i = code.indexOf(startWord, i + startWord.length()))
                        spanUnitList.add(SpanUnit.of(context, highlightUnit, i, i + startWord.length()));
            else {
                boolean isStarting = true;
                int index = 0;
                for (String startWord : highlightUnit.strings)
                    for (String endWord : highlightUnit.strings)
                        for (int i = code.indexOf(startWord); i > -1; i = code.indexOf(isStarting ? startWord : endWord, i + 1),
                                index += isStarting ? startWord.length() : endWord.length())
                            if (isStarting) {
                                index = i;
                                isStarting = false;
                            } else {
                                spanUnitList.add(SpanUnit.of(context, highlightUnit, index, i));
                                isStarting = true;
                                /*if (highlightUnit == HighlightUnits.CUSTOM_FUNCTION) {
                                    String functionName = code.substring(index + startWord.length() - 1, i);
                                    int functionNameLength = functionName.length();
                                    for (int j = code.indexOf(functionName); j > -1; j = code.indexOf(functionName, j + functionNameLength))
                                        newSpanUnitList.add(SpanUnit.of(editText.getContext(), highlightUnits, j, j + functionNameLength));
                                }*/
                            }
            }
        }
        handler.post(() -> {
            editable.clearSpans();
            spanUnitList.forEach(spanUnit -> editable.setSpan(spanUnit.characterStyle, spanUnit.start, spanUnit.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
        });
    }

}
