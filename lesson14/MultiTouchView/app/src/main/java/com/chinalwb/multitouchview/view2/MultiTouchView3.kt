package com.chinalwb.multitouchview.view2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.chinalwb.multitouchview.Utils

class MultiTouchView3 (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var path = Path()
    private var pathSparseArray = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(2)
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0 until pathSparseArray.size()) {
            var path = pathSparseArray.valueAt(i)
            canvas!!.drawPath(path, paint)
        }
        canvas!!.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var pointerId = event!!.getPointerId(event.actionIndex)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                var newPath = Path()
                pathSparseArray.append(pointerId, newPath)
                newPath.moveTo(event.getX(event.actionIndex), event.getY(event.actionIndex))
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    var currentPointerId = event.getPointerId(i)
                    var cPath = pathSparseArray.get(currentPointerId)
                    cPath.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_POINTER_UP-> {
                pathSparseArray.remove(pointerId)
                invalidate()
            }
        }
        return true
    }
}