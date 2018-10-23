package com.chinalwb.lesson06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.Utils;

public class Dashboard extends View {

    // 仪表盘的半径
    private static final float RADIUS = Utils.dp2px(80);
    private static final float LENGTH = Utils.dp2px(60);
    // Paint
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 起始角度
    private static final int START_ANGLE = 150;
    // 扫过的角度
    private static final int SWIPE_ANGLE = 360 - START_ANGLE + (180 - START_ANGLE);
    // Path
    private static Path path = new Path();
    // 分割线的个数
    private final int COUNT = 20;
    // 指向哪一个
    private int POINT_TO = 1;

    private int width, height, dividerWidth, cx, cy, tx, ty;
    private PathEffect pathEffect;


    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStrokeWidth(Utils.dp2px(2));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.cx = w / 2;
        this.cy = h / 2;
        this.dividerWidth = (int) Utils.dp2px(2);

        path.addArc(cx - RADIUS,
                cy - RADIUS,
                cx + RADIUS,
                cy + RADIUS,
                START_ANGLE,
                SWIPE_ANGLE);

        PathMeasure pathMeasure = new PathMeasure(path, false);
        float pathLen = pathMeasure.getLength();
        float advance = (pathLen - dividerWidth) / COUNT;
        Path shape = new Path();
        shape.addRect(0, 0, dividerWidth, Utils.dp2px(10), Path.Direction.CCW);
        pathEffect = new PathDashPathEffect(shape, advance, 0, PathDashPathEffect.Style.ROTATE);

        double degree = getDegreeByPointer(POINT_TO);
        tx = (int) (Math.cos(Math.toRadians(degree)) * LENGTH + cx);
        ty = (int) (Math.sin(Math.toRadians(degree)) * LENGTH + cy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
        paint.setPathEffect(pathEffect);
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);
        canvas.drawLine(cx, cy, tx, ty, paint);
    }


    private double getDegreeByPointer(int pointer) {
        return START_ANGLE + (SWIPE_ANGLE / COUNT) * pointer;
    }
}
