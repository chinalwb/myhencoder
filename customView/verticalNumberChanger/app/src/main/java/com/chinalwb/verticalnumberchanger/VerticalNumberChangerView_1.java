package com.chinalwb.verticalnumberchanger;


import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

@TargetApi(16)
public class VerticalNumberChangerView_1 extends View implements Runnable {

    private final float GAP = Utils.dp2px(10);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float width, height;
    private float cx, cy;
    private Rect bounds = new Rect();
    private String format = "%02d";
    private float offsetY = 0;
    private float downX, downY, originalOffsetY;
    private float MAX_HEIGHT;
    private int maxNumber = 60;
    private float UNIT_HEIGHT = 0;
    private GestureDetectorCompat gestureDetectorCompat;
    private GestureDetector.SimpleOnGestureListener onGestureListener;
    private ObjectAnimator objectAnimator;
    private OverScroller overScroller;

    public VerticalNumberChangerView_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(50));
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAlpha(150);

        overScroller = new OverScroller(getContext());
        onGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                downX = e.getX();
                downY = e.getY();
                originalOffsetY = offsetY;
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent down, MotionEvent move, float distanceX, float distanceY) {
                offsetY -= distanceY;
                fixOffsetY(offsetY);
//                Log.e("XX", "onscroll offset y == " + offsetY);
                invalidate();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent down, MotionEvent move, float velocityX, float velocityY) {
                overScroller.fling(
                        0,
                        (int) offsetY,
                        (int) velocityX,
                        (int) velocityY,
                        0, //(int) -(MAX_HEIGHT - getMeasuredHeight()),
                        0,
                        Integer.MIN_VALUE, //(int) -(MAX_HEIGHT - getMeasuredHeight()) * 2,
                        Integer.MAX_VALUE//(int) (MAX_HEIGHT - getMeasuredHeight()) * 2
                );

                postOnAnimation(VerticalNumberChangerView_1.this);
                return false;
            }
        };
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), onGestureListener);
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            int currY = overScroller.getCurrY();
            fixOffsetY(currY);
//            Log.e("XX", "fling offsetY == " + offsetY);
            invalidate();
            postOnAnimation(this);
        } else {
            fixOffsetPos(offsetY);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
        this.cx = this.width / 2;
        this.cy = this.height / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        paint.getTextBounds("99", 0, 2, bounds);
        int textWidth = bounds.width();
        int textHeight = bounds.height();

        float measuredWidth = textWidth + GAP * 2;
        float measuredHeight = textHeight * 3 + GAP * 6;
        setMeasuredDimension((int) measuredWidth, (int) measuredHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                fixOffsetPos(offsetY);
                break;
        }

        return gestureDetectorCompat.onTouchEvent(event);
    }

    private void fixOffsetPos(float offset) {
        float modResult = offset % UNIT_HEIGHT;
        if (Math.abs(modResult) > 0 && Math.abs(modResult) < UNIT_HEIGHT / 2f) {
            offsetY = offset - modResult;
        } else if (Math.abs(modResult) >= UNIT_HEIGHT / 2f) {
            offsetY = offset - (UNIT_HEIGHT - Math.abs(modResult));
        }
//        Log.e("XX", "offset == " + offset + ", modResult == " + modResult + ", UNIT_HEIGHT / 2F == " + (UNIT_HEIGHT / 2f) + ", offsetY == " + offsetY);

        fixOffsetY(offsetY);
        getObjectAnimator(offset, offsetY).start();
    }

    private void fixOffsetY(float offset) {
//        Log.e("XX", "offset == " + offset);
        float viewHeight = MAX_HEIGHT - getMeasuredHeight();
        if (offset < 0) {
            if (offset > -viewHeight) {
                offsetY = offset;
            } else {
                offset = offset % viewHeight;
                offsetY = offset;
            }
        } else if (offset > 0) {
            if (offset < viewHeight) {
                offset += -viewHeight;
                offsetY = offset;
            } else {
                offset = offset % viewHeight;
                offset += -viewHeight;
                offsetY = offset;
            }
        }

//        Log.e("XX", "--> final offset Y == " + offsetY);

        getCurrentNumber();
    }

    private void getCurrentNumber() {
        int currentNumber = (int) Math.abs(offsetY / UNIT_HEIGHT);
        if (currentNumber == maxNumber) {
            currentNumber = 0;
        }
        Log.e("XX", "Current Number == " + currentNumber); // + ", offset y == " + offsetY + ", unit height == " + UNIT_HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.getTextBounds("99", 0, 2, bounds);
        int textWidth = bounds.width();
        int textHeight = bounds.height();

        canvas.translate(0, offsetY);
        // 文字的绘制
        //
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText(String.format(format, maxNumber - 1), cx, cy - offset - textHeight - GAP * 2, paint);

        float baseY = cy - offset;
        paint.setColor(Color.WHITE);
        paint.setAlpha(150);


        // 00 ~ maxNumber - 2, + maxNumber - 1, 00, 01
        for (int i = 0; i < maxNumber + 2; i++) {
            String numberStr = String.format(format, i);
            if (i > maxNumber - 1) {
                numberStr = String.format(format, i - maxNumber);
            }
            canvas.drawText(numberStr, cx, baseY + i * GAP * 2 + textHeight * i, paint);
        }
        int totalNumbers = maxNumber + 3;
        if (MAX_HEIGHT == 0) {
            MAX_HEIGHT = totalNumbers * textHeight + totalNumbers * 2 * GAP;
            UNIT_HEIGHT = textHeight + GAP * 2;
        }

//        paint.getTextBounds("99", 0, 2, bounds);
//        int textWidth = bounds.width();
//        int textHeight = bounds.height();
//
//
//
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(Utils.dp2px(1));
//        paint.setColor(Color.MAGENTA);
//        float left = (this.width - textWidth) / 2;
//        float top = (this.height - textHeight) / 2;
//        float right = left + textWidth;
//        float bottom = top + textHeight;
//        canvas.drawRect(left, top, right, bottom, paint);
//
//        paint.setStyle(Paint.Style.STROKE);
//        float fmTop = cy + fontMetrics.top;
//        canvas.drawLine(0, fmTop, this.width, fmTop, paint);
//
//        float fmAscent = cy + fontMetrics.ascent;
//        canvas.drawLine(0, fmAscent, this.width, fmAscent, paint);
//
//        float fmBase = cy;
//        canvas.drawLine(0, fmBase, this.width, fmBase, paint);
//
//        float fmDescent = cy + fontMetrics.descent;
//        canvas.drawLine(0, fmDescent, this.width, fmDescent, paint);
//
//        float fmBottom = cy + fontMetrics.bottom;
//        canvas.drawLine(0, fmBottom, this.width, fmBottom, paint);
    }

    private ObjectAnimator getObjectAnimator(float startValue, float endValue) {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "offsetY", startValue, endValue);
        }

        objectAnimator.setFloatValues(startValue, endValue);

        return objectAnimator;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
        invalidate();
    }
}
