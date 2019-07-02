package com.chinalwb.taglayout.v1;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TagLayout extends ViewGroup {


    private ArrayList<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int tagLayoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tagLayoutHeight = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        int widthUsed = 0;
        int heightUsed = 0;
        int lineHeight = 0;
        int maxLineWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);

            // 计算换行
            int childWidth = child.getMeasuredWidth();
            if (widthUsed + childWidth > tagLayoutWidth) {
                // New line
                maxLineWidth = Math.max(maxLineWidth, widthUsed);
                widthUsed = 0;
                heightUsed += lineHeight;
                lineHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            int childHeight = child.getMeasuredHeight();
            // 设定view的bounds
            Rect rect;
            if (childrenBounds.size() <= i) {
                rect = new Rect();
                childrenBounds.add(rect);
            } else {
                rect = childrenBounds.get(i);
            }

            rect.set(widthUsed, heightUsed, widthUsed + childWidth, heightUsed + childHeight);

            // 记录当前行高
            lineHeight = Math.max(lineHeight, childHeight);
            widthUsed += childWidth;
        }

        if (heightUsed == 0) {
            heightUsed = lineHeight;
        } else {
            heightUsed += lineHeight;
        }

        heightUsed = resolveSize(heightUsed, heightMeasureSpec);
        setMeasuredDimension(tagLayoutWidth, heightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            Rect rect = childrenBounds.get(i);
            Log.e("XX", "rect[" + i + "] == " + rect);
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

}
