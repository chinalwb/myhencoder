package com.chinalwb.taglayout.v2

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView

class SquareImageView (context: Context, attributeSet: AttributeSet): ImageView(context,
        attributeSet) {

    private var ow = 0
    private var oh = 0

    var changed = false
    public fun changeSize() {
        if (ow > oh) {
            oh = ow
        } else {
            ow = oh
        }

        changed = true
        Log.w("XX", "Changed size!! $ow , $oh")
        requestLayout()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.w("XX", "on size changed !! new width: $w, $h -- old width $oldw, old height $oldh")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mw = MeasureSpec.getSize(widthMeasureSpec)
        var mh = MeasureSpec.getSize(heightMeasureSpec)
        if (ow == 0) {
            ow = mw
        }
        if (oh == 0) {
            oh = mh
        }

        if (mw > mh) {
            mw = mh
        } else {
            mh = mw
        }

        if (changed) {
            setMeasuredDimension(ow, oh)
            return
        }
        setMeasuredDimension(mw, mh)
        Log.w("XX", "first time: $mw $mh")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.w("XX", "ondraw !! $changed")
    }
}