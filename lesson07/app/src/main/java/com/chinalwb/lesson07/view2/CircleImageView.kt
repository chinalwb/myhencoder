package com.chinalwb.lesson07.view2

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View

import com.chinalwb.lesson07.R
import com.chinalwb.lesson07.Utils


class CircleImageView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap? = null
    private var savedArea: RectF? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(R.drawable.rengwuxian, Utils.dp2px(300).toInt())

        savedArea = RectF(0F, 0F, bitmap!!.width.toFloat(), bitmap!!.height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 第一种方式:
        val path = Path()
        val cx = bitmap!!.width / 2
        val cy = bitmap!!.height / 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), cx.toFloat(), paint);
        path.addCircle(cx.toFloat(), cy.toFloat(), cx.toFloat() - 10, Path.Direction.CCW)
        canvas.save()
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
        canvas.restore()

        canvas.drawLine(10f, 100f, width.toFloat(), 100f, paint)

        // 第二种方式:
//        canvas.drawOval(0F, 0F, bitmap!!.width.toFloat(), bitmap!!.height.toFloat(), paint)
//        var saved:Int = canvas.saveLayer(savedArea, paint)
//        canvas.drawOval(10F, 10F, bitmap!!.width.toFloat() - 10F, bitmap!!.height.toFloat() - 10F,
//                paint)
//        var transferMode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        paint.xfermode = transferMode
//        canvas.drawBitmap(bitmap, 0F, 0F, paint)
//        canvas.restoreToCount(saved)

    }

    private fun getBitmap(resId: Int, targetWidth: Int): Bitmap {
        val resources = resources
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = targetWidth
        return BitmapFactory.decodeResource(resources, resId, options)
    }
}
