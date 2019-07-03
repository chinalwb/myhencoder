package com.chinalwb.taglayout.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.chinalwb.taglayout.R

class TouchView (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {


    var viewName: String = ""
    init {
        var typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TouchView)
        viewName = typedArray.getString(R.styleable.TouchView_viewName)
        typedArray.recycle()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.w("XX", "View --> DispatchTouchEvent name is : $viewName")
        return super.dispatchTouchEvent(event)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == ACTION_UP) {
            performClick()
        }

        var eventName = ""
        when (event?.actionMasked) {
            ACTION_DOWN -> {
                eventName = "ACTION_DOWN"
            }
            ACTION_MOVE -> {
                eventName = "ACTION_MOVE"
            }
            ACTION_UP -> {
                eventName = "ACTION_UP"
            }
            ACTION_CANCEL -> {
                eventName = "ACTION_CANCEL"
            }
            else -> {
                eventName = event?.actionMasked.toString()
            }
        }

        Log.w("XX", "View --> In TouchView name is : $viewName :::: $eventName")
        return true
    }
}