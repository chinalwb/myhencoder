package com.chinalwb.lesson06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.Utils;

public class Pie extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius = Utils.dp2px(80);
    private int cx, cy, width, height, left, top, right, bottom;
    private static final int[] angles = {40, 50, 60, 100, 110};
    private static final int[] colors = {
            Color.RED,
            Color.BLACK,
            Color.MAGENTA,
            Color.CYAN,
            Color.BLUE
    };
    private int SPLIT_INDEX = 3;
    private int OFFSET = (int) Utils.dp2px(20);

    public Pie(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.cx = w / 2;
        this.cy = h / 2;
        this.left = (int) (cx - radius);
        this.top = (int) (cy - radius);
        this.right = (int) (cx + radius);
        this.bottom = (int) (cy + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int len = angles.length;
        int currentAngle = 0;
        for (int i = 0; i < len; i++) {
            paint.setColor(colors[i]);
            if (i == SPLIT_INDEX) {
                canvas.save();
                int degree = currentAngle + angles[i] / 2;
                int dx = (int) (Math.cos(Math.toRadians(degree)) * OFFSET);
                int dy = (int) (Math.sin(Math.toRadians(degree)) * OFFSET);
                canvas.translate(dx, dy);
            }
            canvas.drawArc(left, top, right, bottom, currentAngle, angles[i], true, paint);
            currentAngle += angles[i];
            if (i == SPLIT_INDEX) {
                canvas.restore();
            }
        }
    }
}
