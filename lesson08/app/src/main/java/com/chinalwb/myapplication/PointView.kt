package com.chinalwb.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View

class PointView (context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var point = Point(0, 0)
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.strokeWidth = Util().dp2px(30)
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPoint(point.x.toFloat(), point.y.toFloat(), paint)
    }
}