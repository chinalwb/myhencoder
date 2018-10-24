package com.chinalwb.lesson06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.Utils;

public class PathFillTypeView extends View {

    private float padding = Utils.dp2px(10);
    private float width = Utils.dp2px(200);
    private float height = Utils.dp2px(300);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PathFillTypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.addRect(padding, padding, padding + width, padding + height, Path.Direction.CCW);

        float cx = padding + width / 2;
        float cy = padding + height;
        float radius = width / 2;
        path.addCircle(cx, cy, radius, Path.Direction.CCW);

        canvas.drawPath(path, paint);


    }
}
