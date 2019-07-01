package com.chinalwb.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import com.chinalwb.materialedittext.Utils

class ChessBackgroundDrawable(var padding: Float) : Drawable() {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.strokeWidth = Utils.dp2px(1)
        paint.color = Color.DKGRAY
    }

    override fun draw(canvas: Canvas) {

        // 背景色
        paint.shader = LinearGradient(
                0F,
                0F,
                this.bounds.width().toFloat() / 2,
                this.bounds.height().toFloat() / 2,
                Color.YELLOW,
                Color.GREEN,
                Shader.TileMode.MIRROR
        )
        canvas.drawRect(
                0F,
                0F,
                this.bounds.width().toFloat(),
                this.bounds.height().toFloat(),
                paint)

        paint.shader = null


        // 棋盘宽高
        var width = this.bounds.right - this.bounds.left - padding * 2
        var height = width + width / 9F

        var paddingY = (this.bounds.height() - height) / 2
        canvas.translate(padding, paddingY)


        // 楚河汉界 Y 起点
        var riverYStart = height / 9 * 4
        var riverYEnd = height / 9 * 5

        paint.textSize = Utils.dp2px(20)
        paint.textAlign = Paint.Align.CENTER
        var river = "楚河"
        var bound = "汉界"

        var rect = Rect()
        paint.getTextBounds(river, 0, river.length, rect)
        var y = height / 2 - (rect.bottom + rect.top) / 2

        Log.w("XX", "rect == left == ${rect.left}, top = ${rect.top}, right == ${rect.right}, " +
                "bottom == ${rect.bottom}")
        Log.w("XX", "paint font metrics descent == " + paint.fontMetrics.descent + ", ascent = "
                + paint.fontMetrics.ascent)

//        canvas.drawLine(0F, y, width, y, paint)
//        canvas.drawLine(0F, y - rect.height(), width, y - rect.height(), paint)

//        var textHeight = paint.fontMetrics.descent + paint.fontMetrics.ascent
//        var y = height / 2 - textHeight/ 2
        canvas.drawText(river, width / 4, y, paint)
        canvas.drawText(bound, width / 4 * 3, y, paint)


//        var left = 0F
//        var top = 0F
//        var right = width
//        var bottom = height
//
//        paint.color = Color.CYAN
//        canvas.drawRect(left, top, right, bottom, paint)
//
//
//        paint.color = Color.DKGRAY

        val verticalLinesCount = 9
        var xInterval = width / (verticalLinesCount - 1)

        // 上半部分的竖线, 只能画到一半
        var verticalLines = FloatArray(4 * (verticalLinesCount))
        for (i in 0 until verticalLinesCount * 4 step 4) {
            var startX = i / 4 * xInterval
            var startY = 0F
            var endX = startX
            var endY = riverYStart
            verticalLines[i] = startX
            verticalLines[i + 1] = startY
            verticalLines[i + 2] = endX
            verticalLines[i + 3] = endY
        }


        var horizontalLinesCount = 10
        var horizontalLines = FloatArray(4 * horizontalLinesCount)
        var yInterval = height / (horizontalLinesCount - 1)
        for (i in 0 until horizontalLinesCount * 4 step 4) {
            var startX = 0F
            var startY = i / 4 * yInterval
            var endX = width
            var endY = startY
            horizontalLines[i] = startX
            horizontalLines[i + 1] = startY
            horizontalLines[i + 2] = endX
            horizontalLines[i + 3] = endY
        }


        var verticalLines2 = FloatArray(4 * verticalLinesCount)
        for (i in 0 until verticalLinesCount * 4 step 4) {
            var startX = i / 4 * xInterval
            var offsetY: Float = if (i == 0 || i == (verticalLinesCount - 1) * 4) 0F else
                height / 9F
            var startY = riverYStart + offsetY
            var endX = startX
            var endY = height
            verticalLines2[i] = startX
            verticalLines2[i + 1] = startY
            verticalLines2[i + 2] = endX
            verticalLines2[i + 3] = endY
        }

        canvas.drawLines(verticalLines, paint)
        canvas.drawLines(horizontalLines, paint)
        canvas.drawLines(verticalLines2, paint)
        canvas.translate(-padding, -paddingY)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return if (paint.alpha == 0) {
            PixelFormat.TRANSPARENT
        } else {
            PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}