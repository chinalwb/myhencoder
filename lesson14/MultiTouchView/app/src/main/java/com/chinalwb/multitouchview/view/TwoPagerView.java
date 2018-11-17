package com.chinalwb.multitouchview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

public class TwoPagerView extends ViewGroup {

    private float width, height;

    private boolean isScrolling;
    private float downX, downY, scrollX;
    private ViewConfiguration viewConfiguration;
    private VelocityTracker velocityTracker;
    private OverScroller overScroller;

    public TwoPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        viewConfiguration = ViewConfiguration.get(getContext());
        velocityTracker = VelocityTracker.obtain();
        overScroller = new OverScroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();
            // Log.e("XX", "00 --> Index == " + i + ", Child width == " + cw + ", child height == " + ch);
        }

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();
//            Log.e("XX", "11 --> Index == " + i + ", Child width == " + cw + ", child height == " + ch);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;

//        Log.e("XX", "22 --> view width == " + this.width + ", view height == " + this.height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        int lastChildRight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            float left = lastChildRight;
            float top = 0;
            float right = left + child.getMeasuredWidth();
            float bottom = this.height;
            lastChildRight = (int) right;
            child.layout((int) left, (int) top, (int) right, (int) bottom);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(ev);

//        Log.e("XX", "action == " + ev.getActionMasked());
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isScrolling = false;
                downX = ev.getX();
                downY = ev.getY();
                scrollX = getScrollX();
//                Log.e("XX", "downX == " + downX);
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("XX", "ex == " + ev.getX());
                if (!isScrolling) {
                    float currentX = ev.getX();
                    float dx = currentX - downX;
//                    Log.e("XX", "onInterceptTouchEvent: dx == " + dx);
                    if (Math.abs(dx) > viewConfiguration.getScaledPagingTouchSlop()) {
                        isScrolling = true;
//                        Log.e("XX", "Starts scrolling === " + isScrolling);
                        getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }
                break;
        }

//        Log.e("XX", "Is scrolling === " + isScrolling);
        return false;
    }

    @TargetApi(16)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("XX", "scrolling now? " + isScrolling);
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 记录坐标
                downX = event.getX();
                downY = event.getY();
                scrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 触摸移动反馈
                float currentX = event.getX();
                float dx = downX - currentX + scrollX;
                if (dx > getWidth()) {
                    dx = getWidth();
                } else if (dx < 0) {
                    dx = 0;
                }
                scrollTo((int) dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                // 执行翻页
                velocityTracker.computeCurrentVelocity(1000 * 1, viewConfiguration.getScaledMaximumFlingVelocity());
                float xVelocity = velocityTracker.getXVelocity();
                int minV = viewConfiguration.getScaledMinimumFlingVelocity();
                Log.e("XX", "xv = " + xVelocity + ", minV == " + minV);

                scrollX = getScrollX();
                int targetPage;
                if (Math.abs(xVelocity) < minV) {
                    targetPage = scrollX > getWidth() / 2 ? 1 : 0;
                } else {
                    targetPage = xVelocity < 0 ? 1 : 0;
                }
                float scrollDistance = targetPage == 1 ? getWidth() - scrollX : -scrollX;
                overScroller.startScroll((int) scrollX, 0, (int) scrollDistance, 0);
                postInvalidateOnAnimation();
                break;
        }

        return true;
    }

    @TargetApi(16)
    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            int cx = overScroller.getCurrX();
            scrollTo(cx, 0);
            postInvalidateOnAnimation();
        }
    }
}
