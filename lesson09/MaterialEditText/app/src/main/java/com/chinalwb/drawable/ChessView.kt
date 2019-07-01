package com.chinalwb.drawable

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.chinalwb.materialedittext.Utils

class ChessView (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val padding = Utils.dp2px(20)

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var meshDrawable = ChessBackgroundDrawable(padding)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        meshDrawable.setBounds(left, top, right, bottom)
        meshDrawable.draw(canvas!!)
    }
}