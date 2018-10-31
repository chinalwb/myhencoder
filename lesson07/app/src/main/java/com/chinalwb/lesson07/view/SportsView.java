package com.chinalwb.lesson07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chinalwb.lesson07.Utils;

public class SportsView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float stroke_width = Utils.dp2px(20);
    private float radius = Utils.dp2px(150);
    private int width, height, cx, cy;
    private Path path;
    private float textSize = Utils.dp2px(50);
    private Rect bounds;

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.cx = this.width / 2;
        this.cy = this.height / 2;
        this.path = new Path();
        this.path.addArc(this.cx - radius, this.cy - radius, this.cx + radius, this.cy + radius, -90, 215);
        this.bounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(stroke_width);

        canvas.drawCircle(cx, cy, radius, paint);

        paint.setColor(Color.MAGENTA);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawPath(this.path, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("aGg", cx, cy - offset, paint);
    }
}
