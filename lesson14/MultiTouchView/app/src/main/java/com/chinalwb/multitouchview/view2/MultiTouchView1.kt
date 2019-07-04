package com.chinalwb.multitouchview.view2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.chinalwb.multitouchview.R
import com.chinalwb.multitouchview.Utils

class MultiTouchView1 (context: Context, attributeSet: AttributeSet) : View(context,
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
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(event.actionIndex)
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                var pointerIndex = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(pointerIndex) - downX + lastOffsetX
                offsetY = event.getY(pointerIndex) - downY + lastOffsetY
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                trackingPointerId = event.getPointerId(event.actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                var upPointerId = event.getPointerId(event.actionIndex)

                if (upPointerId == trackingPointerId) {
                    var newTrackingIndex = event.pointerCount - 1
                    if (event.actionIndex == event.pointerCount - 1) {
                        newTrackingIndex = event.pointerCount - 2
                    }
                    trackingPointerId = event.getPointerId(newTrackingIndex)
                    downX = event.getX(newTrackingIndex)
                    downY = event.getY(newTrackingIndex)
                    lastOffsetX = offsetX
                    lastOffsetY = offsetY
                }
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

}