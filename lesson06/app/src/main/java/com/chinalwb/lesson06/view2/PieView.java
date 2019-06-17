package com.chinalwb.lesson06.view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.Utils;

public class PieView extends View {
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final RectF oval = new RectF();
    private static final float RADIUS = Utils.dp2px(150);
    private static final int[] angles = {30, 100, 80, 50, 100};
    private static final int[] colors = {
            Color.parseColor("#30FF90"),
            Color.parseColor("#30F100"),
            Color.parseColor("#FF0980"),
            Color.parseColor("#901150"),
            Color.parseColor("#F0F100")
    };
    private static final int PULLED_OUT_INDEX = 4;
    private static int cx, cy;

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = getWidth() / 2;
        cy = getHeight() / 2;
        float left = cx - RADIUS;
        float top = cy - RADIUS;
        float right = cx + RADIUS;
        float bottom = cy + RADIUS;
        oval.left = left;
        oval.top = top;
        oval.right = right;
        oval.bottom = bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            if (i == PULLED_OUT_INDEX) {
                Point point = getPulledOutCenterPoint();
                canvas.save();
                canvas.translate(point.x, point.y);
            }
            canvas.drawArc(oval, currentAngle, angles[i], true, paint);
            currentAngle += angles[i];

            if (i == PULLED_OUT_INDEX) {
                canvas.restore();
            }
        }
    }

    public static Point getPulledOutCenterPoint() {
        int pulledOutAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            if (i == PULLED_OUT_INDEX) {
                pulledOutAngle += angles[i] / 2;
                break;
            }
            pulledOutAngle += angles[i];
        }
        Point point = new Point();
        point.x = (int) (Math.cos(Math.toRadians(pulledOutAngle)) * Utils.dp2px(20));
        point.y = (int) (Math.sin(Math.toRadians(pulledOutAngle)) * Utils.dp2px(20));
        return point;
    }
}
