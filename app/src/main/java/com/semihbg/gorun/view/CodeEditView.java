package com.semihbg.gorun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

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
        StringBuilder lineStringBuilder=new StringBuilder();
        IntStream.range(0,15).forEach(i->lineStringBuilder
                .append(System.lineSeparator()));
        setText((getText()==null?"":getText().toString())
                .concat(lineStringBuilder.toString()));

        List<BackgroundColorSpan> backgroundColorSpanList=new ArrayList<>();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String code=getText().toString();
                for int i
                for(int i=code.indexOf("func");i>-1;i=code.indexOf("func",i+4)){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
