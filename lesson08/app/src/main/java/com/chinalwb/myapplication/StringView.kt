package com.chinalwb.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class StringView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        for (i in 1 .. 100) {

        }
    }
    companion object {
        val strings = arrayOf(
            "001",
            "002",
            "003",
            "004",
            "005",
            "006",
            "007",
            "008",
            "009",
            "010",
            "011",
            "012",
            "013",
            "014",
            "015",
            "016",
            "017",
            "018"
        )

    }


    var index: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.textSize = Util().dp2px(30)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawText(strings[index], width / 2F, height / 2F, paint)
    }
}