package com.chinalwb.taglayout.v2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import com.chinalwb.taglayout.R
import com.chinalwb.taglayout.Utils
import kotlinx.android.synthetic.main.activity_main.view.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class CircleView(context: Context, attributeSet: AttributeSet, defStyle: Int) : View
(context, attributeSet, defStyle),
        OnClickListener {

    private var radius = Utils.dp2px(10)
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var ow = 0
    private var oh = 0
    private var restarted  = false
    private var strokeWidth = 1F
    private var strokeColor = Color.BLACK


    init {
        var typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleView)
        this.strokeColor = typedArray.getColor(R.styleable.CircleView_strokeColor, strokeColor)
        this.strokeWidth = typedArray.getDimension(R.styleable.CircleView_strokeWidth, Utils
                .dp2px(1))
        typedArray.recycle()

        paint.strokeWidth = this.strokeWidth
        paint.style = Paint.Style.STROKE
        paint.color = this.strokeColor
        initCircleView()
    }


    constructor(context: Context, attributeSet: AttributeSet) : this(context,
            attributeSet, 0)

    private fun initCircleView() {
        this.minimumHeight = (radius * 2).toInt()
        this.minimumWidth = (radius * 2).toInt()

        this.setOnClickListener(this)
        this.isClickable = true
        this.isSaveEnabled = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        Log.w("XX", "original measured width ${this.measuredWidth}, ${this.measuredHeight}")
//        var mw = radius * 2 + paint.strokeWidth
//        var mh = radius * 2 + paint.strokeWidth
//        setMeasuredDimension(mw.toInt(), mh.toInt())

        var mh = getImprovedDefaultHeight(heightMeasureSpec)
        var mw = getImprovedDefaultWidth(widthMeasureSpec)

        setMeasuredDimension(mw, mh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.w("XX", "on Draw width ${width}, height ${height}, radius $radius")
        canvas?.drawCircle(width / 2F, height / 2F, radius, paint)
    }

    private fun getImprovedDefaultHeight(measureSpec: Int): Int {
        if (restarted && oh > 0) {
            return oh
        }

        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.UNSPECIFIED -> {
                return hGetMaximumHeight()
            }
            MeasureSpec.EXACTLY -> {
                return specSize
            }
            MeasureSpec.AT_MOST -> {
                return hGetMinimumHeight()
            }
        }
        return hGetMinimumHeight()
    }

    private fun getImprovedDefaultWidth(measureSpec: Int): Int {
        if (restarted && ow > 0) {
            return ow
        }

        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.UNSPECIFIED -> {
                return hGetMaximumWidth()
            }
            MeasureSpec.EXACTLY -> {
                return specSize
            }
            MeasureSpec.AT_MOST -> {
                return hGetMinimumWidth()
            }
        }
        return hGetMinimumHeight()
    }

    private fun hGetMinimumHeight() = this.suggestedMinimumHeight + paint.strokeWidth.toInt()

    private fun hGetMinimumWidth() = this.suggestedMinimumWidth + paint.strokeWidth.toInt()

    fun hGetMaximumHeight(): Int {
        return 0
    }

    fun hGetMaximumWidth(): Int {
        return 0
    }

    override fun onClick(v: View?) {
        radius *= 1.2F
        adjustMinimumHeight()
        requestLayout()
        invalidate()
    }



    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun adjustMinimumHeight() {
        this.minimumHeight = (radius * 2).toInt() + paint.strokeWidth.toInt()
        this.minimumWidth = (radius * 2).toInt() + paint.strokeWidth.toInt()

        this.oh = this.minimumHeight.toInt()
        this.ow = this.minimumWidth.toInt()
    }

    override fun onSaveInstanceState(): Parcelable? {
        var parcelable = super.onSaveInstanceState()

        var savedState = SavedState(parcelable)
        savedState.radius = this.radius
        savedState.width = this.ow
        savedState.height = this.oh
        savedState.restarted = 1

        Log.w("XX", "on save instance state!!")
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }


        var b = state as SavedState
        super.onRestoreInstanceState(state.superState)
        this.radius = b.radius
        this.ow = b.width
        this.oh = b.height
        this.restarted = b.restarted > 0

        Log.w("XX", "on restore state ${this.radius}")

    }

    class SavedState(parcelable: Parcelable?) : BaseSavedState(parcelable) {
        var radius: Float = 1F
        var width = 1
        var height = 1
        var restarted = 1

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeFloat(radius)
            out?.writeInt(width)
            out?.writeInt(height)
            out?.writeInt(restarted)
        }

        override fun toString(): String {
            return "CircleView radius : $radius"
        }

        private constructor(inParcel: Parcel) : this(null) {
            radius = inParcel.readFloat()
            width = inParcel.readInt()
            height = inParcel.readInt()
            restarted = inParcel.readInt()
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<SavedState> {
            override fun createFromParcel(source: Parcel?): SavedState {
                return SavedState(source!!)
            }

            override fun newArray(size: Int): Array<SavedState> {
                return Array<SavedState>(size) { i -> SavedState(null) }
            }
        }
    }
}