package com.chinalwb.me2

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.chinalwb.materialedittext.R
import com.chinalwb.materialedittext.Utils

class MaterialEditText2 (context: Context, attributeSet: AttributeSet) : EditText(context,
        attributeSet) {

    companion object {
        val TEXT_SIZE = Utils.dp2px(12)
        val TEXT_MARGIN_TOP = Utils.dp2px(10)
    }

    var floatingTextY = TEXT_SIZE + TEXT_MARGIN_TOP
    var floatingShown = false
    var objectAnimator: ObjectAnimator? = null
    var useFloatingLabel = false
        set(value) {
            if (value != field) {
                onUseFloatingLabelChanged(value)
            }
            field = value
        }
    var textWatcher: TextWatcher? = null
    var animPercent = 0F
        set(value) {
            field = value
            invalidate()
        }


    private var padding = Rect()

    init {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (useFloatingLabel) {
                    showFloatingLabel(s!!.isNotEmpty())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        var typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialEditText2)
        for (i in 0 until attributeSet.attributeCount) {
            Log.w("XX", "name == ${attributeSet.getAttributeName(i)}, value == ${attributeSet
                    .getAttributeValue(i)}")
        }
        var floatingLabelEnabled = typedArray.getBoolean(R.styleable
        .MaterialEditText2_floatingLabelEnabled,false)

        Log.w("XX", "floating label enabled == " + floatingLabelEnabled)
        background.getPadding(padding)
        this.useFloatingLabel = floatingLabelEnabled
    }


    private fun onUseFloatingLabelChanged(useNow: Boolean) {
        if (useNow) {
            this.setPadding(
                    paddingLeft,
                    padding.top + TEXT_SIZE.toInt() + TEXT_MARGIN_TOP.toInt(),
                    paddingRight,
                    paddingBottom
            )
            this.addTextChangedListener(textWatcher)
        } else {
            this.setPadding(
                    paddingLeft,
                    padding.top,
                    paddingRight,
                    paddingBottom
            )
            this.removeTextChangedListener(textWatcher)
        }
        showFloatingLabel(this.text.isNotEmpty() && useNow)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var alpha = 0xff * animPercent
        paint.alpha = alpha.toInt()

        var offsetY = TEXT_SIZE * (1 - animPercent)
        canvas!!.drawText(hint.toString(), paddingLeft.toFloat(), floatingTextY + offsetY, paint)
    }

    private fun showFloatingLabel(show: Boolean) {
        if (floatingShown != show) {
            floatingShown = show

            if (objectAnimator == null) {
                objectAnimator = ObjectAnimator.ofFloat(this, "animPercent", 1F)
            }

            if (show) {
                objectAnimator!!.start()
            } else {
                objectAnimator!!.reverse()
            }
        }
    }
}