package com.chinalwb.multitouchview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.chinalwb.multitouchview.R;
import com.chinalwb.multitouchview.Utils;

/**
 * 协作型 多点触控
 * 忽略个体 注重整体
 */
public class MultiTouchView2 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float offsetX, offsetY, downX, downY, originalX, originalY;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
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



//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;
//        int pointerCount = event.getPointerCount();
//        float totalX = 0;
//        float totalY = 0;
//        for (int i = 0; i < pointerCount; i++) {
//            if (isPointerUp && i == event.getActionIndex()) {
//                continue;
//            }
//
//            float x = event.getX(i);
//            totalX += x;
//
//            float y = event.getY(i);
//            totalY += y;
//        }
//
//        int count = isPointerUp ? pointerCount - 1 : pointerCount;
//        float focusX = totalX / count;
//        float focusY = totalY / count;
//
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_POINTER_DOWN:
//            case MotionEvent.ACTION_POINTER_UP:
//                downX = focusX;
//                downY = focusY;
//
//                originalX = offsetX;
//                originalY = offsetY;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                offsetX = focusX - downX + originalX;
//                offsetY = focusY - downY + originalY;
//                invalidate();
//                break;
//        }
//
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerCount = event.getPointerCount();
                float totalX = 0;
                float totalY = 0;
                for (int i = 0; i < pointerCount; i++) {
                    float x = event.getX(i);
                    totalX += x;

                    float y = event.getY(i);
                    totalY += y;
                }
                downX = totalX / pointerCount;
                downY = totalY / pointerCount;

                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerCount = event.getPointerCount();
                Log.e("XX", "MOVE Pointer count == " + pointerCount);
                totalX = 0;
                totalY = 0;
                for (int i = 0; i < pointerCount; i++) {
                    float x = event.getX(i);
                    totalX += x;

                    float y = event.getY(i);
                    totalY += y;
                }
                float eventX = totalX / pointerCount;
                float eventY = totalY / pointerCount;

                offsetX = eventX - downX + originalX;
                offsetY = eventY - downY + originalY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int actionIndex = event.getActionIndex();
                pointerCount = event.getPointerCount();
                totalX = 0;
                totalY = 0;
                for (int i = 0; i < pointerCount; i++) {
                    if (actionIndex != i) {
                        float x = event.getX(i);
                        totalX += x;

                        float y = event.getY(i);
                        totalY += y;
                    }
                }
                downX = totalX / (pointerCount - 1);
                downY = totalY / (pointerCount - 1);

                originalX = offsetX;
                originalY = offsetY;
                break;
        }

        return true;
    }
}
