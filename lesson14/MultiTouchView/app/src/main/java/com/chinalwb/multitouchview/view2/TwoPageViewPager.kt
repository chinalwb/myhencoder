package com.chinalwb.multitouchview.view2

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import androidx.annotation.RequiresApi
import kotlin.math.abs

class TwoPageViewPager (context: Context, attributeSet: AttributeSet) : ViewGroup(context,
        attributeSet) {

    private var velocityTracker = VelocityTracker.obtain()
    private var viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private var overScroller = OverScroller(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        var childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until  childCount) {
            if (i == 0) {
                getChildAt(i).layout(0, 0, width, height)
            } else {
                getChildAt(i).layout(width, 0, width * 2, height)
            }
        }
    }


    var downX = 0F
    var downScrollX = 0F

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                var offsetX = downX - event.x + downScrollX
                if (offsetX > width) {
                    offsetX = width.toFloat()
                } else if (offsetX < 0) {
                    offsetX = 0F
                }

                scrollTo(offsetX.toInt(), 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, viewConfiguration
                        .scaledMaximumFlingVelocity.toFloat())
                var xVelocity = velocityTracker.xVelocity

                var targetPage = 0
                if (abs(xVelocity) < viewConfiguration.scaledMinimumFlingVelocity) {
                    targetPage = if (scrollX > width / 2) 1 else 0
                } else {
                    targetPage = if (xVelocity < 0) 1 else 0
                }
                var scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(scrollX, 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            var cx = overScroller.currX
            scrollTo(cx, 0)
            postInvalidateOnAnimation()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }

        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
            }
            MotionEvent.ACTION_MOVE -> {
                var dx = ev.x - downX
                if (abs(dx) > viewConfiguration.scaledPagingTouchSlop) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
        }
        return false
    }
}