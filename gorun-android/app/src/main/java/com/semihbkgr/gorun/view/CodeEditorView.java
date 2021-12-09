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
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setTextSize(40);
        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());
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
        if (getText() != null) {
            int lineCount = stringLineCount(getText().toString());
            if (lineCount < 20) {
                String padding = "";
                for (int i = lineCount; i <= 20; i++)
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
