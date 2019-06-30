package com.chinalwb.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import com.chinalwb.materialedittext.Utils

class MyDrawable(var floatingText: String) : Drawable() {
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var floatingX: Float = 0F
    var floatingY: Float = 0F

    init {
        paint.textSize = Utils.dp2px(12)
        paint.color = Color.GREEN
        paint.textAlign = Paint.Align.LEFT
    }
    override fun draw(canvas: Canvas) {
        val width = bounds.right - bounds.left
        var height = bounds.bottom - bounds.top
        canvas.drawText(floatingText, floatingX, floatingY, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return if (paint.alpha == 0) { PixelFormat.TRANSPARENT } else { PixelFormat.TRANSLUCENT }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}