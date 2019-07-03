package com.chinalwb.taglayout.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.LinearLayout

class TouchLayout (context: Context, attributeSet: AttributeSet) : LinearLayout(context,
        attributeSet) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var fw = measuredWidth + 100
        var fh = measuredHeight
        setMeasuredDimension(fw, fh)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        for (i in 0 until childCount) {
            var child = getChildAt(i)
//            if (i == 1) {
//                child.layout(child.left - 100, child.top, child.right - 100, child.bottom)
//            }
            if (i == 1) {
                child.layout(child.left + 100, child.top, child.right + 100, child.bottom)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.w("XX", "ViewGroup >>>> DispatchTouchEvent!!")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.w("XX", "ViewGroup >>>> onInterceptTouchEvent!!")
        return super.onInterceptTouchEvent(ev)
//        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var consumed = super.onTouchEvent(event)
        Log.w("XX", "ViewGroup >>>> OnTouch!!! $consumed")
        return consumed
    }
}