package com.chinalwb.multitouchview.view2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.chinalwb.multitouchview.R
import com.chinalwb.multitouchview.Utils
import kotlin.math.abs

class Gallery (context: Context, attributeSet: AttributeSet) : View (context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap1: Bitmap? = null
    private var bitmap2: Bitmap? = null
    private var camera = Camera()
    private var cx = 0F
    private var cy = 0F
    private var upDegree2 = 0F
    private var downDegree2 = 0F
    private var upAlpha1 = 0
    private var downDegree1 = 180F
    private var index = 0
    private var max = 3

    init {
        camera.setLocation(0F, 0F, -12 * context.resources.displayMetrics.density)
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL
        paint.strokeWidth = Utils.dp2px(2)
        paint.textSize = Utils.dp2px(16)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap1 = Utils.getBitmap(context.resources, R.drawable.x, width)
        bitmap2 = Utils.getBitmap(context.resources, R.drawable.y, width)

        cx = width / 2F
        cy = height / 2F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (index == 0) {
            paint.alpha = 255
            canvas!!.drawText("当前是第一张图片", cx, cy / 2, paint)
        }

        // bitmap 1
        var top1 = (height - bitmap1!!.height) / 2F
        if (abs(upDegree2) > 0) {
            paint.alpha = upAlpha1
            canvas!!.save()
            canvas.translate(cx, cy)
            canvas.clipRect(-cx, -cy, cx, 0F)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(bitmap1!!, 0F, top1, paint)
            canvas.restore()
        }

        // bitmap 2
        paint.alpha = 255
        var top = (height - bitmap2!!.height) / 2F
        if (abs(upDegree2) <= 90) {
            canvas!!.save()
            canvas.translate(cx, cy)
            camera.save()
            camera.rotateX(upDegree2)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-cx, -cy, cx, 0F)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(bitmap2!!, 0F, top, paint)
            canvas.restore()
        }

        if (abs(upDegree2) > 90) {
            paint.alpha = 255 - upAlpha1
        }
        canvas!!.save()
        canvas.translate(cx, cy)
        camera.save()
        camera.rotateX(downDegree2)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-cx, 0F, cx, cy)
        canvas.translate(-cx, -cy)
        canvas.drawBitmap(bitmap2!!, 0F, top, paint)
        canvas.restore()



        // bitmap 1
        if (abs(upDegree2) > 90 || abs(downDegree2) > 0) {
            paint.alpha = upAlpha1
            canvas.save()
            canvas.translate(cx, cy)
            camera.save()
            camera.rotateX(-downDegree1)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-cx, 0F, cx, cy)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(bitmap1!!, 0F, top1, paint)
            canvas.restore()
        }
    }

    var downY = 0F

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                var offsetY = event.y - downY
                if (offsetY > 0) {
                    upDegree2 = - offsetY / cy * 180
                    if (index > 0) {
                        upAlpha1 = (offsetY / cy * 255).toInt()
                        if (upAlpha1 > 255) {
                            upAlpha1 = 255
                        }
                        downDegree1 = 180 - upDegree2

                        if (downDegree1 > 360F) {
                            downDegree1 = 360F
                        }
                    }
                } else {
                    downDegree2 = - offsetY / cy * 180
                    if (index < max - 1) {
                        upAlpha1 = abs((offsetY / cy * 255).toInt())
                        Log.w("XX", "up alpha1 $upAlpha1")
                        if (upAlpha1 > 255) {
                            upAlpha1 = 255
                        }
                        downDegree1 = 0F
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                upDegree2 = 0F
                downDegree2 = 0F
                downDegree1 = 180F
                upAlpha1 = 0
                invalidate()
            }
        }
        return true
    }
}