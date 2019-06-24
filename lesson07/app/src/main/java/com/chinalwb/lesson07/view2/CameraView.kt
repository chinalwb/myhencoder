package com.chinalwb.lesson07.view2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.chinalwb.lesson07.R
import com.chinalwb.lesson07.Utils

class CameraView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val targetWidth: Int = Utils.dp2px(200).toInt()
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mLeft: Float = 0F
    private var mTop: Float = Utils.dp2px(100)
    private var mCamera: Camera = Camera()


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mLeft = (this.width - targetWidth) / 2.toFloat()

        mCamera.rotateX(45F)
        mCamera.setLocation(0F,0F, -6 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val bitmap: Bitmap = getBitmap(R.drawable.rengwuxian, targetWidth)

        // 绘制上半部分
        canvas?.save()
        canvas?.translate(mLeft + targetWidth / 2, mTop + targetWidth / 2)
        canvas?.rotate(-20F)
        canvas?.clipRect(
                -(mLeft + targetWidth / 2), // left
                -(mTop + targetWidth / 2), // top
                targetWidth / 2 + mLeft,  // right
                0F  // bottom
        )
        canvas?.rotate(20F)
        canvas?.translate(-(mLeft + targetWidth / 2), -(mTop + targetWidth / 2))
        canvas?.drawBitmap(bitmap, mLeft, mTop, mPaint)
        canvas?.restore()

        // 绘制下半部分
        canvas?.save()
        canvas?.translate(mLeft + targetWidth / 2, mTop + targetWidth / 2)
        canvas?.rotate(-20F)
        mCamera.applyToCanvas(canvas)
        canvas?.clipRect(
                -(mLeft + targetWidth / 2), // left
                0F, // top
                targetWidth / 2 + mLeft,  // right
                targetWidth / 1F // bottom
        )
        canvas?.rotate(20F)
        canvas?.translate(-(mLeft + targetWidth / 2), -(mTop + targetWidth / 2))
        canvas?.drawBitmap(bitmap, mLeft, mTop, mPaint)
        canvas?.restore()
    }

    private fun getBitmap(resId: Int, targetWdith: Int): Bitmap {
        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(this.resources, resId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = targetWidth
        return BitmapFactory.decodeResource(this.resources, resId, options)
    }

}