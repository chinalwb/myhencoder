package com.chinalwb.scalableimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouchView1 extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float offsetX, offsetY, downX, downY, originalOffsetX, originalOffsetY;
    private int trackPointerId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmap = Utils.getBitmap(getResources(), R.drawable.x, (int) Utils.dp2px(200));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                Log.e("XX", "downX == " + downX + ",, downY == " + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackPointerId);
                float cx = event.getX(index);
                float cy = event.getY(index);
                Log.e("XX", "22222 move ---> cx == " + cx + ", cy == " + cy);
                offsetX = cx  - downX + originalOffsetX;
                offsetY = cy - downY + originalOffsetY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                index = event.getActionIndex();
                trackPointerId = event.getPointerId(index);
                Log.e("XX", "00000 down ---> tracking index == " + index);
                downX = event.getX(index);
                downY = event.getY(index);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointerCount = event.getPointerCount();
                index = event.getActionIndex();
                int actionPointerId = event.getPointerId(index);
                if (actionPointerId == trackPointerId) {
                    if (index == pointerCount - 1) {
                        index = pointerCount - 2;
                    } else {
                        index = pointerCount - 1;
                    }
                    trackPointerId = event.getPointerId(index);
                    downX = event.getX(index);
                    downY = event.getY(index);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }
                break;
        }
        return true;
    }
}
