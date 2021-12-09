package com.semihbkgr.gorun.highlight;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class DefaultCodeHighlighter extends UnitBasedHighlighter {

    private final TextView textView;
    private final AtomicReference<List<CharacterStyle>> characterStyleSpanListReference;

    public DefaultCodeHighlighter(TextView textView, List<? extends HighlightUnit> highlightUnitList) {
        super(highlightUnitList);
        this.textView = textView;
        this.characterStyleSpanListReference = new AtomicReference<>(Collections.emptyList());
    }

    @Override
    public void highlight() {
        String code = textView.getText().toString();
        List<SpanUnit> spanUnitList = new ArrayList<>();
        for (HighlightUnit highlightUnit : highlightUnitList) {
            if (highlightUnit.isIntermittent())
                for (String startWord : highlightUnit.startSyntaxes)
                    for (int i = code.indexOf(startWord); i > -1; i = code.indexOf(startWord, i + startWord.length()))
                        spanUnitList.add(SpanUnit.of(textView.getContext(), highlightUnit, i, i + startWord.length()));
            else {
                boolean isStarting = true;
                int index = 0;
                for (String startWord : highlightUnit.startSyntaxes)
                    for (String endWord : highlightUnit.endSyntaxes)
                        for (int i = code.indexOf(startWord); i > -1; i = code.indexOf(isStarting ? startWord : endWord, i + 1),
                                index += isStarting ? startWord.length() : endWord.length())
                            if (isStarting) {
                                index = i;
                                isStarting = false;
                            } else {
                                spanUnitList.add(SpanUnit.of(textView.getContext(), highlightUnit, index, i));
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
        System.out.println("highlighted, count: " + spanUnitList.size());
        textView.post(() -> {
            List<CharacterStyle> characterStyleList = spanUnitList.stream()
                    .map(spanUnit -> spanUnit.characterStyle)
                    .collect(Collectors.toList());
            List<CharacterStyle> oldCharacterStyleList = characterStyleSpanListReference.getAndSet(characterStyleList);
            textView.post(() -> {
                Editable editable = textView.getEditableText();
                oldCharacterStyleList.forEach(editable::removeSpan);
                spanUnitList.forEach(spanUnit -> editable.setSpan(spanUnit.characterStyle, spanUnit.start, spanUnit.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
            });
        });
    }

}
