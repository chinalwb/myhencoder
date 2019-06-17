package com.chinalwb.lesson06.view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.Utils;

public class DashboardView extends View {
    private static final float RADIUS = Utils.dp2px(150);
    private static final float INNER_RADIUS = Utils.dp2px(140);
    private static final float POINTER_RADIUS = Utils.dp2px(120);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int DASH_COUNT = 10;

    private int startAngle = 150;
    private int sweepAngle = 240;

    private int cx, cy;

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));
        cx = getWidth() / 2;
        cy = getHeight() / 2;
        float left = cx - RADIUS;
        float top = cy - RADIUS;
        float right = cx + RADIUS;
        float bottom = cy + RADIUS;

        boolean useCenter = false;
        canvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, useCenter, paint);


        float[] angles = new float[DASH_COUNT];
        for (int i = 0; i < DASH_COUNT; i++) {
            angles[i] = startAngle + (float) sweepAngle / (DASH_COUNT - 1) * i;
            double startX = cx + Math.cos(Math.toRadians(angles[i])) * INNER_RADIUS;
            double startY = cy + Math.sin(Math.toRadians(angles[i])) * INNER_RADIUS;
            canvas.drawPoint((float) startX, (float) startY, paint);
            double stopX = cx + Math.cos(Math.toRadians(angles[i])) * RADIUS;
            double stopY = cy + Math.sin(Math.toRadians(angles[i])) * RADIUS;
            canvas.drawLine((float) startX, (float) startY, (float) stopX, (float) stopY, paint);
        }

        canvas.drawPoint(cx, cy, paint);

        Point endPoint = getEndPoint(1);
        canvas.drawLine(cx, cy, endPoint.x, endPoint.y, paint);
    }

    private Point getEndPoint(int target) {
        Point point = new Point();
        float targetAngle = startAngle + (float) sweepAngle / (DASH_COUNT - 1) * target;
        double x = cx + Math.cos(Math.toRadians(targetAngle)) * POINTER_RADIUS;
        double y = cy + Math.sin(Math.toRadians(targetAngle)) * POINTER_RADIUS;
        point.x = (int) x;
        point.y = (int) y;
        return point;
    }
}
