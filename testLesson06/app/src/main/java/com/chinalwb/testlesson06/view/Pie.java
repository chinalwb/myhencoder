package com.chinalwb.testlesson06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.testlesson06.Utils;

public class Pie extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width, height, cx, cy;
    private int[] angles = {30, 60, 100, 120, 30, 20};
    private int[] colors = {
            Color.BLUE,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK,
            Color.RED,
            Color.DKGRAY
    };
    private int focusIndex = 3;
    private float offsetDistance = Utils.dp2px(20);
    private RectF rectF;

    public Pie(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.width = w;
        this.height = h;
        this.cx = this.width / 2;
        this.cy = this.width / 2;

        int padding = (int) Utils.dp2px(20);

        this.rectF = new RectF(padding, padding, this.width - padding, this.width - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawRect(0, 0, this.width, this.width, paint);
        paint.setStyle(Paint.Style.FILL);

        int len = angles.length;
        int currentAngle = 90;
        for (int i = 0 ; i < len; i++) {
            float dx = 0;
            float dy = 0;
            if (i == this.focusIndex) {
                dx = (float) (Math.cos(Math.toRadians(currentAngle + angles[i] / 2)) * offsetDistance);
                dy = (float) (Math.sin(Math.toRadians(currentAngle + angles[i] / 2)) * offsetDistance);
                canvas.translate(dx, dy);
            }
            paint.setColor(colors[i]);

            canvas.drawArc(rectF, currentAngle, angles[i], true, paint);
            if (i == this.focusIndex) {
                canvas.translate(-dx, -dy);
            }
            currentAngle += angles[i];
        }
    }
}
