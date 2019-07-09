package com.chinalwb.lesson06.view2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.chinalwb.lesson06.Utils

class TextPathView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var path = Path()

    private val text = "Hello Android"
    init {
        paint.textSize = Utils.dp2px(50)
        paint.style = Paint.Style.FILL
        paint.isFakeBoldText = false
        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = Utils.dp2px(5)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawText(text, width / 2F, height / 4F, paint)


        paint.getTextPath(text, 0, text.length, width/2F, height / 2F, path)
        paint.color = Color.GREEN
        canvas!!.drawText(text, width / 2F, height / 2F, paint)
        paint.style = Paint.Style.STROKE
        canvas!!.drawPath(path, paint)
    }
}