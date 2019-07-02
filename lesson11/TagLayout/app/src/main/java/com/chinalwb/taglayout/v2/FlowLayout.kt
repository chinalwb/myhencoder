package com.chinalwb.taglayout.v2

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

class FlowLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var currentLeft = 0
        var currentTop = 0
        var containerWidth = this.measuredWidth
        var containerHeight = this.measuredHeight

        var rowMaxHeight = 0

        var lineMaxWidth = 0
        var totalHeight = 0
        for (i in 0 until childCount) {
            var child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
//            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec,
//                    currentTop)
            Log.w("XX", "child.measuredWidth = ${child.measuredWidth}, measured height == ${child
                    .measuredHeight}, margin top == ${(child.layoutParams as MarginLayoutParams)
                    .topMargin}")

            if (currentLeft + child.measuredWidth > containerWidth) {
                // next line
                currentLeft = 0
                currentTop += Math.max(rowMaxHeight, child.measuredHeight)
                Log.w("XX", ">> row max height $rowMaxHeight, current top = $currentTop")
                rowMaxHeight = 0

                lineMaxWidth = Math.max(lineMaxWidth, currentLeft)
            }
            var thisTop = currentTop
            if ((child.layoutParams as MarginLayoutParams).topMargin > 0) {
                thisTop += (child.layoutParams as MarginLayoutParams).topMargin
            }
            child.top = thisTop
            child.left = currentLeft
            child.right = child.left + child.measuredWidth
            child.bottom = child.top + child.measuredHeight
            currentLeft += child.measuredWidth
            rowMaxHeight = Math.max(rowMaxHeight, child.measuredHeight + (child.layoutParams as MarginLayoutParams).topMargin)

            lineMaxWidth = Math.max(lineMaxWidth, currentLeft)
            totalHeight = currentTop + rowMaxHeight
            Log.w("XX", "container width / height $lineMaxWidth / $totalHeight")
        }

        var resolvedWidth = View.resolveSize(lineMaxWidth, widthMeasureSpec)
        var resolvedHeight = View.resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resolvedWidth, resolvedHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            var child = getChildAt(i)
            child.layout(
                    child.left,
                    child.top,
                    child.right,
                    child.bottom
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.w("XX", "FlowLayout on draw!!")


    }

    // 用代码 FlowLayout#addView 的方式添加的子view 用这个方法来设置 MarginLayoutParams
    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    // 从 XML 文件当中加载的子view 用这个方法来设置 MarginLayoutParams
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}