package com.chinalwb.multitouchview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.chinalwb.multitouchview.Utils;

import java.net.PasswordAuthentication;

import androidx.annotation.Nullable;

public class MultiTouchView3 extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SparseArray<Path> paths = new SparseArray<>(5);

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(5));
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.valueAt(i), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);
        Log.e("XX", "pointer id == " + pointerId);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path path = new Path();
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex));
                paths.put(pointerId, path);
                Log.e("XX", "paths size == " + paths.size());
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    pointerId = event.getPointerId(i);
                    Path pointerPath = paths.get(pointerId);
                    pointerPath.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                path = paths.get(pointerId);
                if (null != path) {
                    paths.remove(pointerId);
                    invalidate();
                }
                break;
        }
        return true;
    }
}
