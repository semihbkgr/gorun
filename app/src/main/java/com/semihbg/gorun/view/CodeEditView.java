package com.semihbg.gorun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbg.gorun.util.TextWatcherAdapter;
import com.semihbg.gorun.view.highlight.CodeHighlighter;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public class CodeEditView extends androidx.appcompat.widget.AppCompatEditText {

    private Rect rect;
    private Paint paint;
    private CodeHighlighter codeHighlighter;

    public CodeEditView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setTextSize(40);
        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());
        StringBuilder lineStringBuilder=new StringBuilder();
        IntStream.range(0,15).forEach(i->lineStringBuilder
                .append(System.lineSeparator()));
        setText((getText()==null?"":getText().toString())
                .concat(lineStringBuilder.toString()));

        codeHighlighter=new CodeHighlighter(this);
        codeHighlighter.update();
        addTextChangedListener((TextWatcherAdapter)
                (s, start, before, count) -> codeHighlighter.update());


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
