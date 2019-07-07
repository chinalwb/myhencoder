package com.chinalwb.multitouchview.view2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.chinalwb.multitouchview.R
import com.chinalwb.multitouchview.Utils
import kotlin.math.*

class Gallery (context: Context, attributeSet: AttributeSet) : View (context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var camera = Camera()
    private var cx = 0F
    private var cy = 0F

    // Default
    private var upDegree = arrayOf(0F, 0F, -180F)
    private var downDegree = arrayOf(180F, 0F, 0F)

//    private var upDegree = arrayOf(0F, 0F, -180F)
//    private var downDegree = arrayOf(180F, 0F, 0F)

//    private var upAlpha[index][index - 1] = 0
//    private var downAlpha[index - 1] = 0
    private var upAlpha = arrayOf(0, 0, 255)
    private var downAlpha = arrayOf(255, 0, 255)

    private var index = 0
    private var max = 3
    private var bitmaps: Array<Bitmap?> = arrayOfNulls(3)

    init {
        camera.setLocation(0F, 0F, -12 * context.resources.displayMetrics.density)
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL
        paint.strokeWidth = Utils.dp2px(2)
        paint.textSize = Utils.dp2px(16)
    }

    private fun reset() {
        upDegree = arrayOf(0F, 0F, -180F)
        downDegree = arrayOf(180F, 0F, 0F)

        upAlpha = arrayOf(0, 0, 255)
        downAlpha = arrayOf(255, 0, 255)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmaps[0] = Utils.getBitmap(context.resources, R.drawable.y, width)
        bitmaps[1] = Utils.getBitmap(context.resources, R.drawable.x, width)
        bitmaps[2] = Utils.getBitmap(context.resources, R.drawable.z, width)

        cx = width / 2F
        cy = height / 2F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.w("xx", "index == ${index}")
        if (index == 0) {
            paint.alpha = 255
            canvas!!.drawText("当前是第一张图片", cx, cy / 2, paint)
        }

        drawPreviousBitmap(canvas)

        drawCurrentBitmap(canvas)

        drawNextBitmap(canvas)
    }

    private fun drawPreviousBitmap(canvas: Canvas?) {
        if (index == 0) {
            return
        }
        // bitmap previous
        val previousBitmap = bitmaps[index - 1]
        var top1 = (height - previousBitmap!!.height) / 2F
        if (abs(upDegree[1]) > 0 || abs(downDegree[1]) > 90) {
            paint.alpha = upAlpha[0]
            canvas!!.save()
            canvas.translate(cx, cy)
            camera.save()
            camera.rotateX(upDegree[0])
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-cx, -cy, cx, 0F)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(previousBitmap, 0F, top1, paint)
            canvas.restore()
        }

        // bitmap previous
        // bitmap previous lower
//        Log.w("xx", "upDegree[$index] == ${upDegree[1]}")
        if (abs(upDegree[1]) > 89.99F) {
            var bottomTopOffset = cy * cos(Math.toRadians(abs(downDegree[0].toDouble()))).toFloat()
            if (bottomTopOffset < 0) {
                bottomTopOffset = 0F
            }
            paint.alpha = downAlpha[index - 1]
            canvas!!.save()
            canvas.translate(cx, cy)
            camera.save()
            camera.rotateX(downDegree[0])
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-cx, 0F, cx, bottomTopOffset)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(previousBitmap, 0F, top1, paint)
            canvas.restore()
        }
    }

    private fun drawCurrentBitmap(canvas: Canvas?) {
        // bitmap current
        // bitmap current upper
        paint.alpha = 255
        val currentBitmap = bitmaps[index]
        var top = (height - currentBitmap!!.height) / 2F

//        if (downDegree[1] > 90) {
//            paint.alpha = upAlpha[index]
//        }

        canvas!!.save()
        canvas.translate(cx, cy)
        camera.save()
        camera.rotateX(upDegree[1])
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-cx, -cy, cx, 0F)
        canvas.translate(-cx, -cy)
        canvas.drawBitmap(currentBitmap!!, 0F, top, paint)
        canvas.restore()

        // currentBitmap lower
//        if (abs(upDegree[1]) > 90) {
//            paint.alpha = 255 - upAlpha[index]
//        }


        Log.w("xx", "upDegree[$index] == ${upDegree[1]}")
        var bottomTopOffset = 0F
        if (downDegree[0] < 90) {
            bottomTopOffset = cy * (cos(Math.toRadians(abs(downDegree[0].toDouble()))).toFloat())
        }
        canvas!!.save()
        canvas.translate(cx, cy)
        camera.save()
        camera.rotateX(downDegree[1])
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-cx, bottomTopOffset, cx, cy)
        canvas.translate(-cx, -cy)
        canvas.drawBitmap(currentBitmap!!, 0F, top, paint)
        canvas.restore()
    }

    private fun drawNextBitmap(canvas: Canvas?) {
        if (index == max - 1) {
            return
        }
        // bitmap next
        val nextBitmap = bitmaps[index + 1]
        var top = (height - nextBitmap!!.height) / 2F
        if (downDegree[1] > 89.99F) {
            paint.alpha = downAlpha[2]
            canvas!!.save()
            canvas.translate(cx, cy)
            camera.save()
            camera.rotateX(upDegree[2])
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-cx, -cy, cx, 0F)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(nextBitmap, 0F, top, paint)
            canvas.restore()
        }

        // bitmap next
        // bitmap next lower
        if (downDegree[1] > 0) {
            var bottomTopOffset = cy * cos(Math.toRadians(abs(downDegree[1].toDouble()))).toFloat()
            if (bottomTopOffset < 0) {
                bottomTopOffset = 0F
            }
            paint.alpha = downAlpha[2]
            canvas!!.save()
            canvas.translate(cx, cy)
            camera.save()
            camera.rotateX(downDegree[2])
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-cx, bottomTopOffset, cx, cy)
            canvas.translate(-cx, -cy)
            canvas.drawBitmap(nextBitmap, 0F, top, paint)
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
                    // 从上往下翻 -- 上一页
                    upDegree[1] = - offsetY / cy * 180
                    if (index > 0) {
                        var absDegree = abs(upDegree[1])
                        if (absDegree > 90) {
                            absDegree = 90F
                        }
                        upAlpha[index - 1] = (absDegree / 90 * 255).toInt()
                        downDegree[0] = upDegree[1] + 180
                        if (downDegree[0] < 0) {
                            downDegree[0]= 0F
                        }
                    }
                    if (upDegree[1] < -90) {
                        upDegree[1] = -90F
                    }
                } else {
                    // 从下往上翻 -- 下一页
                    downDegree[1] = - offsetY / cy * 180
                    if (index < max - 1) {
                        var absDegree = abs(downDegree[1])
                        if (absDegree > 90) {
                            absDegree = 90F
                        }
                        downAlpha[2] = abs((absDegree / 90 * 255).toInt())
                        upDegree[2] = downDegree[1] - 180
                        if (upDegree[2] > 0) {
                            upDegree[2] = 0F
                        }
                    }
                    if (downDegree[1] > 90) {
                        downDegree[1] = 90F
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
//                upDegree[1] = 0F
//                downDegree[1] = 0F
//                downDegree[0] = 180F
//                upAlpha[index] = 0

//                Log.w("xx", "downDegree[1] = ${downDegree[1]}")
//                if (downDegree[1] > 1F) {
//                    index++
//                }

                reset()
                index++
                invalidate()
            }
        }
        return true
    }
}