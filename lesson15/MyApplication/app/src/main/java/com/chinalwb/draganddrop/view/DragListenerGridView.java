package com.chinalwb.draganddrop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DragListenerGridView extends ViewGroup implements View.OnTouchListener {

    private int ROWS = 3, COLS = 2;
    private float width, height;
    private View draggingView;

    public DragListenerGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);

        float childWidth = specWidth / COLS;
        float childHeight = (specHeight - 0) / ROWS;

        measureChildren(
                MeasureSpec.makeMeasureSpec((int) childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int) childHeight, MeasureSpec.EXACTLY)
        );
        this.width = specWidth;
        this.height = specHeight;
        this.setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            float left = 0;
            if (i % 2 != 0) {
                left = this.width / COLS;
            }

            int row = i / 2;
            float top = this.height / ROWS * row;

            float width = child.getMeasuredWidth();
            int right = (int) (left + width);

            float height = child.getMeasuredHeight();
            int bottom = (int) (top + height);

            child.layout((int) left, (int) top, right, bottom);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            child.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    draggingView = v;
                    v.startDrag(null, new DragShadowBuilder(v), v, 0);
                    return false;
                }
            });
            child.setOnDragListener(new MyDragListener());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("XX", "On touch v == " + v);
        return true;
    }

    class MyDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getLocalState() == v) {
                        v.setVisibility(View.INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // Sort here
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    v.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.e("XX", "GridView on drag event.");

        return super.onDragEvent(event);
    }
}

