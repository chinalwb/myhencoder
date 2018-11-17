package com.chinalwb.multitouchview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chinalwb.multitouchview.R;
import com.chinalwb.multitouchview.Utils;

import androidx.annotation.Nullable;

/**
 * 接力型 多点触控
 */
public class MultiTouchView1 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float offsetX, offsetY, downX, downY, originalX, originalY;
    private int trackingPointerId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.bitmap = Utils.getBitmap(getResources(), R.drawable.x, (int) Utils.dp2px(200));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = event.getX(index) - downX + originalX;
                offsetY = event.getY(index) - downY + originalY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                index = event.getActionIndex();
                trackingPointerId = event.getPointerId(index);
                downX = event.getX(index);
                downY = event.getY(index);
                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int actionIndex = event.getActionIndex();
                int actionPointerId = event.getPointerId(actionIndex);
                if (actionPointerId == trackingPointerId) {
                    if (actionIndex == event.getPointerCount() - 1) {
                        actionIndex = event.getPointerCount() - 2;
                    } else {
                        actionIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(actionIndex);
                    downX = event.getX(actionIndex);
                    downY = event.getY(actionIndex);
                    originalX = offsetX;
                    originalY = offsetY;
                }
                break;
        }

        return true;
    }
}
