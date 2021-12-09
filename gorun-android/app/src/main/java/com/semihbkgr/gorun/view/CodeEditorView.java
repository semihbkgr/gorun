package com.semihbkgr.gorun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class CodeEditorView extends androidx.appcompat.widget.AppCompatEditText {

    private final Rect rect;
    private final Paint paint;


    public CodeEditorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPadding(70, 10, 50, 10);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(25);
        paint.setAntiAlias(false);
        paint.setSubpixelText(false);
        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(3);
        final int firstBaseline = getBaseline();
        int baseline = firstBaseline;
        int lineCount = 0;
        for (int i = 0; i < getLineCount(); i++) {
            lineCount++;
            canvas.drawText(String.format(Locale.getDefault(), "%03d ", (i + 1)), rect.left + 10, baseline, paint);
            baseline += getLineHeight();
        }
        canvas.drawLine(60, firstBaseline - getLineHeight(), 60, lineCount * getLineHeight() + firstBaseline - getLineHeight(), paint);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (getText() != null) {
            int lineCount = stringLineCount(getText().toString());
            if (lineCount < 10) {
                String padding = "";
                for (int i = lineCount; i <= 10; i++)
                    padding = padding.concat(System.lineSeparator());
                getText().append(padding);
            }
        }
    }

    public void addText(String text) {
        if (getText() != null)
            getText().insert(getSelectionStart(), text);
        else
            setText(text);
    }

    public void quote() {
        addText("\"");
        int index = getSelectionStart();
        addText("\"");
        setSelection(index);
    }

    private int stringLineCount(String str) {
        return str.split(System.lineSeparator()).length + 1;
    }

}
