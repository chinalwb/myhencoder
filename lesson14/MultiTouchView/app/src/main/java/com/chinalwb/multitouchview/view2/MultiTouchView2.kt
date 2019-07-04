package com.chinalwb.multitouchview.view2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.chinalwb.multitouchview.R
import com.chinalwb.multitouchview.Utils

class MultiTouchView2 (context: Context, attributeSet: AttributeSet) : View(context,
        attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bitmap: Bitmap? = null
    var offsetX = 0F
    var offsetY = 0F
    var downX = 0F
    var downY = 0F
    var lastOffsetX = 0F
    var lastOffsetY = 0F
    var trackingPointerId = 0

    init {
        bitmap = Utils.getBitmap(context.resources, R.drawable.x, Utils.dp2px(200).toInt())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var sumX = 0F
        var sumY = 0F

        var pointerUp = event!!.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (i in 0 until event.pointerCount) {
            if (pointerUp && event.actionIndex == i) {
                continue
            }
            sumX += event.getX(i)
            sumY += event.getY(i)
        }
        var pointerCount = if (pointerUp) { event.pointerCount - 1 } else { event.pointerCount }
        var moveX = sumX / pointerCount
        var moveY = sumY / pointerCount

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_POINTER_UP -> {
                downX = moveX
                downY = moveY
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = moveX - downX + lastOffsetX
                offsetY = moveY - downY + lastOffsetY
                invalidate()
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

}