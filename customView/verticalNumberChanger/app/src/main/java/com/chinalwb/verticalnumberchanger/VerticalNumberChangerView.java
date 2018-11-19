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
public class VerticalNumberChangerView extends View implements GestureDetector.OnGestureListener, Runnable {

    private final float GAP = Utils.dp2px(10);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float width, height;
    private float cx, cy;
    private Rect bounds = new Rect();
    private String format = "%02d";
    private float offsetY = 0;
    private float downY, originalOffsetY;
    private int maxNumber = 100;
    private float UNIT_HEIGHT = 0;
    private float textWidth, textHeight, TEXT_OFFSET;
    private int baseIndex = -1;
    private ObjectAnimator objectAnimator;
    private GestureDetectorCompat gestureDetectorCompat;
    private OverScroller overScroller;
    private int selectedNumber = 0;
    private NumberChangeListener numberChangeListener;

    public VerticalNumberChangerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(50));
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAlpha(150);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        TEXT_OFFSET = (fontMetrics.ascent + fontMetrics.descent) / 2;

        paint.getTextBounds("99", 0, 2, bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();
        UNIT_HEIGHT = textHeight + GAP * 2;

        gestureDetectorCompat = new GestureDetectorCompat(getContext(), this);

        overScroller = new OverScroller(getContext());
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

        float measuredWidth = textWidth + GAP * 2;
        float measuredHeight = textHeight * 3 + GAP * 6;
        setMeasuredDimension((int) measuredWidth, (int) measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float validOffsetY = offsetY % UNIT_HEIGHT;
        float validOffsetYFix = offsetY > 0 && validOffsetY != 0f ? -UNIT_HEIGHT : 0;
//         Log.e("XX", "offsetY == " + offsetY + ", valid offset Y == " + validOffsetY + ", UH == " + UNIT_HEIGHT);
        float baseY = cy - TEXT_OFFSET - textHeight - GAP * 2 + validOffsetY + validOffsetYFix;

        // 文字的绘制
        //
        int offsetIndex = (int) (-offsetY / UNIT_HEIGHT);
        int offsetIndexFix = offsetY > 0 && validOffsetY != 0f ? -1 : 0;
        int firstIndex = baseIndex + offsetIndex + offsetIndexFix;
        int drawCount = validOffsetY == 0 ? 3 : 4;

//        Log.e("XX", "first index == " + firstIndex + ", offsetIndex = " + offsetIndex + ", drawCount == " + drawCount);


        for (int i = 0; i < drawCount; i++) {
            int number = (maxNumber + firstIndex) % maxNumber;
            String numberStr = String.format(format, number);
            canvas.drawText(numberStr, cx, baseY, paint);

            firstIndex++;
            baseY += UNIT_HEIGHT;
        }
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
        if (modResult == 0) {
            return;
        }
        if (offset > 0) {
            if (modResult < UNIT_HEIGHT / 2f) {
                offsetY = offset - modResult;
            } else {
                offsetY = offset + (UNIT_HEIGHT - modResult);
            }
        } else if (offset < 0) {
            if (modResult > -UNIT_HEIGHT / 2f) {
                offsetY = offset - modResult;
            } else {
                offsetY = offset - (UNIT_HEIGHT + modResult);
            }
        }
        getCurrentNumberAndFireListener();
//        Log.e("XX", "offset == " + offset + ", offsetY == " + offsetY);
        getObjectAnimator(offset, offsetY).start();
    }

    private int getCurrentNumberAndFireListener() {
        int currentNumber = getCurrentNumber();
        Log.e("XX", "Current Number == " + currentNumber);

        if (currentNumber != this.selectedNumber) {
            if (this.numberChangeListener != null) {
                this.numberChangeListener.onNumberChanged(currentNumber);
            }
            this.selectedNumber = currentNumber;
        }
        return currentNumber;
    }

    public int getCurrentNumber() {
        float validOffsetY = offsetY % UNIT_HEIGHT;
        int offsetIndex = (int) (-offsetY / UNIT_HEIGHT);
        int offsetIndexFix = offsetY > 0 && validOffsetY != 0f ? -1 : 0;
        int firstIndex = baseIndex + offsetIndex + offsetIndexFix;
        int selectIndex = firstIndex + 1;
        int currentNumber = (maxNumber + selectIndex) % maxNumber;
        return currentNumber;
    }

    private ObjectAnimator getObjectAnimator(float start, float end) {
        if (null == this.objectAnimator) {
            this.objectAnimator = ObjectAnimator.ofFloat(this, "offsetY", 0, 0);
        }

        this.objectAnimator.setFloatValues(start, end);
        return this.objectAnimator;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
        invalidate();
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
        offsetY -= distanceY;
        getCurrentNumberAndFireListener();
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        overScroller.fling(0,
                (int) offsetY,
                (int) velocityX,
                (int) velocityY,
                0,
                0,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);

        postOnAnimation(this);
        return false;
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetY = overScroller.getCurrY();
            getCurrentNumberAndFireListener();
            invalidate();
            postOnAnimation(this);
        } else {
            fixOffsetPos(offsetY);
        }
    }

    public NumberChangeListener getNumberChangeListener() {
        return numberChangeListener;
    }

    public void setNumberChangeListener(NumberChangeListener numberChangeListener) {
        this.numberChangeListener = numberChangeListener;
    }

    /**
     * Number change listener
     */
    public interface NumberChangeListener {
        void onNumberChanged(int newNumber);
    }
}
