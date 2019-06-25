package com.chinalwb.myapplication

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View

class CameraView (context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var paint: Paint = Paint(ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap? = null
    private val mLeft: Float = 200F
    private val mTop: Float = 200F
    private var bHeight: Int? = 0
    private var bWidth: Int? = 0

    private var camera = Camera()

    private var bottomFlip: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    private var canvasRotate: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    private var topFlip: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(R.drawable.rengwuxian, 600)
        bHeight = bitmap?.height
        bWidth = bitmap?.width
        camera.setLocation(0F, 0F, -6 * this.resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        // 上半部分
        canvas!!.save()
        canvas.translate((mLeft + bWidth!! / 2F), (mTop + bHeight!! / 2F))
        canvas.rotate(-this.canvasRotate)
        camera.save()
        camera.rotateX(this.topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(
            - (mLeft + bWidth!! / 2F),
            - (mTop + bHeight!! / 2F),
            this.width.toFloat(),
            0F
        )
        canvas.rotate(this.canvasRotate)
        canvas.translate(- (mLeft + bWidth!! / 2F), - (mTop + bHeight!! / 2F))
        canvas.drawBitmap(bitmap, mLeft, mTop, paint)
        canvas.restore()


        // 下半部分
        canvas!!.save()
        canvas.translate((mLeft + bWidth!! / 2F), (mTop + bHeight!! / 2F))
        canvas.rotate(-this.canvasRotate)
        camera.save()
        camera.rotateX(this.bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(
            - (mLeft + bWidth!! / 2F),
            0F,
            this.width.toFloat(),
            bHeight!!.toFloat()
        )
        canvas.rotate(this.canvasRotate)
        canvas.translate(- (mLeft + bWidth!! / 2F), - (mTop + bHeight!! / 2F))
        canvas.drawBitmap(bitmap, mLeft, mTop, paint)
        canvas.restore()
    }

    private fun getBitmap(resId: Int, width: Int): Bitmap {
        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
        BitmapFactory.decodeResource(this.resources, resId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(this.resources, resId, options)
    }
}