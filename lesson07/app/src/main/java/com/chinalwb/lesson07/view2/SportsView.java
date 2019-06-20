package com.chinalwb.lesson07.view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson07.Utils;

public class SportsView extends View {

    private static final float RADIUS = Utils.dp2px(150);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int cx, cy;
    private RectF oval = new RectF();
    private int startAngle, sweepAngle;
    private boolean useCenter;

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.cx = getWidth() / 2;
        this.cy = getHeight() / 2;

        oval.left = cx - RADIUS;
        oval.top = cy - RADIUS;
        oval.right = cx + RADIUS;
        oval.bottom = cy + RADIUS;
        this.startAngle = -90;
        this.sweepAngle = 215;
        this.useCenter = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float strokeWidth = Utils.dp2px(20);
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(cx, cy, RADIUS, paint);


        paint.setColor(Color.MAGENTA);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);


        // 绘制文字
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(Utils.dp2px(2));
        paint.setTextSize(Utils.dp2px(80));
        paint.setColor(Color.MAGENTA);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Rect bounds = new Rect();
        paint.getTextBounds("gggg", 0, 4, bounds);
        float offsetY = (bounds.bottom + bounds.top) / 2;
        canvas.drawText("gggg", cx, cy - offsetY, paint);

        paint.setColor(Color.GREEN);
        float textY = cy - (fontMetrics.descent + fontMetrics.ascent) / 2;
        canvas.drawText("abap", cx, textY, paint);



        paint.setColor(Color.RED);
        canvas.drawPoint(cx, cy, paint);

        int startX = 0, endX = getWidth();
        float descentY = cy + fontMetrics.descent;
        paint.setStrokeWidth(2);
        canvas.drawLine(startX, cy, endX, cy, paint);


//        canvas.drawLine(startX, descentY, endX, descentY, paint);
//
//        paint.setColor(Color.BLUE);
//        float bottomY = cy + fontMetrics.bottom;
//        canvas.drawLine(startX, bottomY, endX, bottomY, paint);
//
//
//        float topY = cy + fontMetrics.top;
//        canvas.drawLine(startX, topY, endX, topY, paint);
//
//        float ascentY = cy + fontMetrics.ascent;
//        canvas.drawLine(startX, ascentY, endX, ascentY, paint);
    }
}
