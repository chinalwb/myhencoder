package com.chinalwb.multitouchview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    private int backgroundColor;
    private int originalColor;
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.originalColor = ((ColorDrawable) this.getBackground()).getColor();
        backgroundColor = originalColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // backgroundColor = Color.DKGRAY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // backgroundColor = originalColor;
                break;
        }
//        invalidate();
        Log.e("XX", "MyView onTouch: " + event.getActionMasked());
        return true; //super.onTouchEvent(event);
    }
}
