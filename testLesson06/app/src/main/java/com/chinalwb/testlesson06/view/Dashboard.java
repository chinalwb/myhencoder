package com.chinalwb.testlesson06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.testlesson06.Utils;

public class Dashboard extends View {

    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static int padding = (int) Utils.dp2px(2);
    private static final int COUNTS = 20;
    private static final float DIVIDER_WIDTH = Utils.dp2px(15f);
    private static final float DIVIDER_HEIGHT = Utils.dp2px(20);
    private static final float HAND_LENGTH = Utils.dp2px(190);

    private int width, height, left, top, right, bottom, cx, cy;
    private int startAngle = 150;
    private float sweepAngle = 180 + (180 - startAngle) * 2;
    private Path path = new Path();
    private PathEffect pathEffect;
    private int pointTo = 20;
    private float lineEndX, lineEndY;


    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;

        this.left = padding;
        this.top = padding;
        this.right = this.width - padding;
        this.bottom = this.width - padding;
        this.cx = this.width / 2;
        this.cy = this.width / 2;

        RectF rectF = new RectF(left, top, right, bottom);

        path.addArc(rectF, this.startAngle, this.sweepAngle);
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        float pathLen = pathMeasure.getLength();
        float advance = (pathLen - DIVIDER_WIDTH) / COUNTS;
        float phase = 0;

        Path shape = new Path();
        shape.addRect(0, 0, DIVIDER_WIDTH, DIVIDER_HEIGHT, Path.Direction.CCW);
        pathEffect = new PathDashPathEffect(shape, advance, phase, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawRect(0, 0, this.width, this.height, paint);


//        canvas.drawPath(path, paint);
        paint.setPathEffect(pathEffect);
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);

        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, Utils.dp2px(5), paint);
        paint.setColor(Color.BLACK);

        paint.setStrokeWidth(2);
         paint.setColor(Color.MAGENTA);
        this.lineEndX = (float) getLineEndX();
        this.lineEndY = (float) getLineEndY();
        float startX = (float) getLineStartX();
        float startY = (float) getLineStartY();
        canvas.drawLine(startX, startY, this.lineEndX, this.lineEndY, paint);
    }

    private double getLineStartX() {
        return cx - Math.cos(Math.toRadians(startAngle + (sweepAngle / COUNTS) * pointTo)) * 50;
    }

    private double getLineStartY() {
        return cx - Math.sin(Math.toRadians(startAngle + (sweepAngle / COUNTS) * pointTo)) * 50;
    }

    private double getLineEndX() {
        return Math.cos(Math.toRadians(startAngle + (sweepAngle / COUNTS) * pointTo)) * HAND_LENGTH + cx;
    }

    private double getLineEndY() {
        return Math.sin(Math.toRadians(startAngle + (sweepAngle / COUNTS) * pointTo)) * HAND_LENGTH + cy;
    }



}
