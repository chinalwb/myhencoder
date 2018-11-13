package com.chinalwb.scalableimageview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.widget.OverScroller;

public class ScalableImageView extends View
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        Runnable,
        ScaleGestureDetector.OnScaleGestureListener {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float width, height, bmw, bmh, bmOffsetX, bmOffsetY;
    private float smallScale, bigScale, extraScale = 2.5f;
    private GestureDetectorCompat gestureDetectorCompat;
    private boolean isBig;
    private float currentScale;
    private ObjectAnimator objectAnimator;
    private float canvasOffsetX, canvasOffsetY;
    private OverScroller overScroller;
    private ScaleGestureDetector scaleGestureDetector;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), this);
        gestureDetectorCompat.setOnDoubleTapListener(this);
        overScroller = new OverScroller(getContext());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        bitmap = Utils.getBitmap(getResources(), R.drawable.y, (int) Utils.dp2px(300));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.bmw = this.bitmap.getWidth();
        this.bmh = this.bitmap.getHeight();
        this.bmOffsetX = (this.width - this.bmw) / 2;
        this.bmOffsetY = (this.height - this.bmh) / 2;

        float widthRatio = this.width / this.bmw;
        float heightRatio = this.height / this.bmh;
        if (widthRatio < heightRatio) {
            this.smallScale = widthRatio;
            this.bigScale = heightRatio * extraScale;
        } else {
            this.smallScale = heightRatio;
            this.bigScale = widthRatio * extraScale;
        }
        this.currentScale = this.smallScale;

        Log.e("XX", "cx == " + this.width / 2 + ", cy == " + this.height / 2);
        Log.e("XX", "small scale == " + smallScale + ", big scale == " + bigScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(canvasOffsetX * scaleFraction, canvasOffsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, this.width / 2, this.height / 2);
        canvas.drawBitmap(bitmap, bmOffsetX, bmOffsetY, paint);
        Log.e("XX", "bit map width == " + this.bitmap.getWidth());

        canvas.drawCircle(this.width / 2, this.height / 2, 20, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return scaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e("XX", "onDown --> x == " + e.getX() + ", y == " + e.getY());
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
    public boolean onScroll(MotionEvent down, MotionEvent move, float distanceX, float distanceY) {
        if (!isBig) {
            return false;
        }
        canvasOffsetX -= distanceX;
        canvasOffsetY -= distanceY;

        fixOffsets();

        Log.e("XX", "cx == " + canvasOffsetX);
        Log.e("XX", "cy == " + canvasOffsetY);

        invalidate();
        return false;
    }

    private void fixOffsets() {
        float maxOffsetX = (this.bmw * bigScale - this.width) / 2;
        float minOffsetX = -maxOffsetX;
        canvasOffsetX = Math.min(canvasOffsetX, maxOffsetX);
        canvasOffsetX = Math.max(canvasOffsetX, minOffsetX);

        float maxOffsetY = (this.bmh * bigScale - this.height) / 2;
        float minOffsetY = -maxOffsetY;
        canvasOffsetY = Math.min(canvasOffsetY, maxOffsetY);
        canvasOffsetY = Math.max(canvasOffsetY, minOffsetY);
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @TargetApi(16)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        overScroller.fling((int) canvasOffsetX, (int) canvasOffsetY, (int) velocityX, (int) velocityY,
                (int) -(this.bmw * bigScale - this.width) / 2,// minX,
                (int) (this.bmw * bigScale - this.width) / 2, //maxX,
                (int) -(this.bmh * bigScale - this.height) / 2, //minY,
                (int) (this.bmh * bigScale - this.height) / 2 //maxY
        );
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
            getAnimator().reverse();
        } else {
            canvasOffsetX = (this.width / 2f - e.getX()) * bigScale / smallScale - (this.width / 2f - e.getX());
            canvasOffsetY = (this.height / 2f - e.getY()) * bigScale / smallScale - (this.height / 2f - e.getY());

            fixOffsets();

//            Log.e("XX", "000--->event get x == " + e.getX() + " event get y == " + e.getY());
//            Log.e("XX", "111--->canvas offset x == " + canvasOffsetX + " coy == " + canvasOffsetY);
            getAnimator().start();
        }
        this.isBig = !this.isBig;
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getAnimator() {
        if (this.objectAnimator == null) {
            this.objectAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale);
        }
//        this.objectAnimator.setFloatValues(smallScale, bigScale);
        return this.objectAnimator;
    }

    @TargetApi(16)
    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            canvasOffsetX = overScroller.getCurrX();
            canvasOffsetY = overScroller.getCurrY();

            fixOffsets();

            invalidate();
            postOnAnimation(this);
        }
    }


    /** 双指缩放 */
    private float startScale;
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        currentScale = startScale * detector.getScaleFactor();
        currentScale = Math.max(currentScale, smallScale);
        currentScale = Math.min(currentScale, bigScale * 1.5f);
        invalidate();
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        startScale = currentScale;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
