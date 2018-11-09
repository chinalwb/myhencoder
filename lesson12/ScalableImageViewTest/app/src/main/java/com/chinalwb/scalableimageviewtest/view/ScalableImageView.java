package com.chinalwb.scalableimageviewtest.view;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.chinalwb.scalableimageviewtest.R;

public class ScalableImageView extends View implements
            GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int sw;
    private int width, height, bitmapWidth, bitmapHeight;
    private float offsetX, offsetY;

    private Bitmap bitmap;
    private boolean isBig;
    private float fraction;
    private ObjectAnimator animator;
    private int smallScale = 1, bigScale = 10;
    private float translateY, oy;

    private GestureDetectorCompat gestureDetectorCompat;
    private OverScroller overScroller;


    float dy;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), this);
        gestureDetectorCompat.setOnDoubleTapListener(this);
        overScroller = new OverScroller(getContext());
        sw = Resources.getSystem().getDisplayMetrics().widthPixels;
        bitmap = Utils.getBitmap(getResources(), R.drawable.timg, sw / bigScale);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.bitmapWidth = this.bitmap.getWidth();
        this.bitmapHeight = this.bitmap.getHeight();
        offsetX = (this.width - this.bitmapWidth) / 2f;
        offsetY = (this.height - this.bitmapHeight) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        dy = -offsetY * fraction;
        canvas.translate(0, dy + translateY);
        Log.e("XX", "translation y == " + (dy + translateY));

        float scale = smallScale + (bigScale - smallScale) * fraction;
        canvas.scale(scale, scale, offsetX + bitmapWidth / 2f, offsetY);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    public ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "fraction", 0, 1);
        }
        return animator;
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

        translateY -= distanceY;
        translateY = Math.min(translateY, 0);
        translateY = Math.max(translateY, height - bitmapHeight * bigScale);
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
        Log.e("XX", "-------------- onfling --------- " + (dy + translateY));
        overScroller.fling(0, (int) (dy + translateY), (int) velocityX, (int) velocityY,
                0, 0,
                height - bitmapHeight * bigScale,
                0
        );

        oy = dy + translateY;
        postOnAnimation(this);
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (this.isBig) {
            translateY = 0;
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
            int cy = overScroller.getCurrY();
//            Log.e("XX", "cy == " + cy + ", oy == " + oy);
            float dy = oy - cy;
//            Log.e("XX", "dy == " + dy);
            oy = cy;
            translateY -= dy;
//            Log.e("XX", "translate y == " + translateY);

            translateY = Math.min(translateY, 0);
            translateY = Math.max(translateY, height - bitmapHeight * bigScale);
            invalidate();
            postOnAnimation(this);
        }
    }
}
