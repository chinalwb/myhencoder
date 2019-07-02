package com.chinalwb.taglayout.v1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chinalwb.taglayout.Utils;

import java.util.Random;

public class ColorTextView extends AppCompatTextView {

    private static final int PADDING = (int) Utils.dp2px(16);
    private static final int[] textSizes = {14, 16, 22, 26, 30, 32};
    private static final int[] COLORS = {
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
    };
    private static final int CORNER_RADIUS = (int) Utils.dp2px(6);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF backgroundRect = new RectF();
    private Random random = new Random();

    public ColorTextView(Context context) {
        this(context, null);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        this.setTextColor(Color.WHITE);
        this.setTextSize(textSizes[random.nextInt(6)]);
        this.setPadding(PADDING, PADDING /2, PADDING, PADDING /2);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        backgroundRect.left = 0;
        backgroundRect.top = 0;
        backgroundRect.right = getWidth();
        backgroundRect.bottom = getHeight();
        paint.setColor(COLORS[random.nextInt(COLORS.length)]);
        canvas.drawRoundRect(backgroundRect, CORNER_RADIUS, CORNER_RADIUS, paint);
        super.onDraw(canvas);
    }

}
