package com.chinalwb.myapplication

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CameraViewRight (context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap? = null
    private var bitmapWidth: Int = 0
    private var bitmapHeight: Int = 0
    private var camera: Camera = Camera()

    private var canvasRotation: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    private var rightFlip: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    private var leftFlip: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    private val LEFT = Util.dp2px(100)
    private val TOP = Util.dp2px(100)

    init {
        bitmap = getBitmap(R.drawable.rengwuxian, Util.dp2px(200).toInt())
        bitmapWidth = bitmap!!.width
        bitmapHeight = bitmap!!.height

        camera.setLocation(0F, 0F, - 6 * Resources.getSystem().displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 左面
        canvas!!.save()
        canvas.translate((LEFT + bitmapWidth / 2F), (TOP + bitmapHeight / 2F))
        canvas.rotate(- canvasRotation)
        camera.save()
        camera.rotateY(leftFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-(bitmapWidth.toFloat()), -(TOP + bitmapHeight / 2F), 0F, bitmapHeight.toFloat())
        canvas.rotate(canvasRotation)
        canvas.translate(-(LEFT + bitmapWidth / 2F), - (TOP + bitmapHeight / 2F))
        canvas.drawBitmap(bitmap, LEFT, TOP, paint)
        canvas.restore()

        // 右面
        canvas.save()
        canvas.translate((LEFT + bitmapWidth / 2F), (TOP + bitmapHeight / 2F))
        canvas.rotate(-canvasRotation)
        camera.save()
        camera.rotateY(rightFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(0F, -(TOP + bitmapHeight / 2F), bitmapWidth.toFloat(), bitmapHeight.toFloat())
        canvas.rotate(canvasRotation)
        canvas.translate(-(LEFT + bitmapWidth / 2F), - (TOP + bitmapHeight / 2F))
        canvas.drawBitmap(bitmap, LEFT, TOP, paint)
        canvas.restore()
    }

    private fun getBitmap(resId: Int, targetWidth: Int): Bitmap {
        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(this.resources, resId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = targetWidth
        return BitmapFactory.decodeResource(this.resources, resId, options)
    }
}

