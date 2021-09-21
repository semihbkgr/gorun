package com.semihbkgr.gorun.view.code;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.util.TextWatcherAdapter;
import com.semihbkgr.gorun.view.code.highlight.CodeHighlighter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class CodeEditor extends androidx.appcompat.widget.AppCompatEditText {

    private final Rect rect;
    private final Paint paint;
    private final CodeHighlighter codeHighlighter;

    public CodeEditor(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setTextSize(40);

        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());

        codeHighlighter = new CodeHighlighter(this);
        codeHighlighter.update();
        addTextChangedListener((TextWatcherAdapter)
                (s, start, before, count) -> codeHighlighter.update());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseline = getBaseline();
        for (int i = 0; i < getLineCount(); i++) {
            canvas.drawText(String.format(Locale.getDefault(), "%3d:", (i + 1)), rect.left, baseline, paint);
            baseline += getLineHeight();
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        int lineCount=stringLineCount(getText().toString());
        if(lineCount<20){
            String padding="";
            for(int i=lineCount;i<=20;i++)
                padding=padding.concat(System.lineSeparator());
            getText().append(padding);
        }
    }

    public void addText(String text) {
        if(getText()!=null)
            getText().insert(getSelectionStart(),text);
        else
            setText(text);
    }

    public void quote(){
        addText("\"");
        int index=getSelectionStart();
        addText("\"");
        setSelection(index);
    }

    private int stringLineCount(String str){
        return str.split(System.lineSeparator()).length+1;
    }

}
