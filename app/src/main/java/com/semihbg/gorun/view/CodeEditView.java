package com.semihbg.gorun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class CodeEditView extends androidx.appcompat.widget.AppCompatEditText {

    private Rect rect;
    private Paint paint;

    public CodeEditView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
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
            canvas.drawText(String.format("%3d:", (i+1)), rect.left, baseline, paint);
            baseline += getLineHeight();
        }
    }

}
