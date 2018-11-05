package com.chinalwb.taglayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int size = Math.max(width, height);
//        setMeasuredDimension(size, size);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        int size = Math.max(width, height);
//        super.layout(l,t,r,b);
         super.layout(l, t, l + size, t + size);
    }
}
