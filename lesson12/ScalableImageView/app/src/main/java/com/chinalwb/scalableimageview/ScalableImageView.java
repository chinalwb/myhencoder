package com.chinalwb.scalableimageview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import android.widget.Scroller;

import java.lang.annotation.Target;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap bitmap;
    private float width, height, bmw, bmh;
    private float bmx, bmy;
    private float smallScale, bigScale, extraScale = 2f;
    private boolean isBig = false;

    private GestureDetectorCompat gestureDetector;
    private ObjectAnimator animator;
    private OverScroller overScroller;
    private float fraction;
    private float offsetX, offsetY, currentTotalOffsetX, currentTotalOffsetY;

    private float ox, oy;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gestureDetector = new GestureDetectorCompat(getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);
        overScroller = new OverScroller(getContext());
        paint.setTextSize(50);
        bitmap = Utils.getBitmap(getResources(), R.drawable.rengwuxian, (int) Utils.dp2px(300));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.bmw = this.bitmap.getWidth();
        this.bmh = this.bitmap.getHeight();
        this.bmx = (this.width - this.bmw) / 2f;
        this.bmy = (this.height - this.bmh) / 2f;
        this.smallScale = Math.min((float) this.width / this.bmw, (float) this.height / this.bmh);
        this.bigScale = Math.max((float) this.width / this.bmw, (float) this.height / this.bmh) * extraScale;
    }

    private ObjectAnimator getAnimator() {
        if (null == animator) {
            animator = ObjectAnimator.ofFloat(this, "fraction", 0, 1);
        }
        return animator;
    }

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        canvas.translate(offsetX, offsetY);
//        Log.e("XX", "offsetY == " + offsetY);
//        float scale = smallScale + (bigScale - smallScale) * fraction;
//        canvas.scale(scale, scale, this.width / 2, this.height / 2);
//        canvas.drawBitmap(bitmap, this.bmx, this.bmy, paint);
//        canvas.restore();


        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case ACTION_DOWN:


                invalidate();
                break;
            case ACTION_MOVE:
                offsetX = event.getX();
                offsetY = event.getY();
                invalidate();
                break;
        }
        return true;
        // return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!isBig) {
            return false;
        }
        offsetX -= distanceX;
        offsetY -= distanceY;
        if (offsetX > 0) {
            offsetX = Math.min(offsetX, (bmw * bigScale - width) / 2f);
        } else {
            offsetX = Math.max(offsetX, (width - bmw * bigScale) / 2f);
        }

        if (offsetY > 0) {
            offsetY = Math.min(offsetY, (bmh * bigScale - height) / 2f);
        } else {
            offsetY = Math.max(offsetY, (height - bmh * bigScale) / 2f);
        }

        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @TargetApi(16)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isBig) {
            return false;
        }

        Log.e("XX", "-------- onfling ------");
        overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                (int) -(bmw * bigScale - width) / 2,
                (int) (bmw * bigScale - width) / 2,
                (int) -(bmh * bigScale - height) / 2,
                (int) (bmh * bigScale - height) / 2);

        postOnAnimation(this);
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (isBig) {
            getAnimator().reverse();
        } else {
            getAnimator().start();
        }
        this.isBig = !this.isBig;
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @TargetApi(16)
    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();

//            Log.e("XX", "cy == " + overScroller.getCurrY());
            invalidate();
            postOnAnimation(this);
        }
    }
}
