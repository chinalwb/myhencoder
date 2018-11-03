package com.chinalwb.materialedittext;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

public class MaterialEditText extends AppCompatEditText {
    private static final int GAP = (int) Utils.dp2px(10);
    private static final int F_TEXT_SIZE = (int) Utils.dp2px(14);
    private static final int F_TOP = (int) Utils.dp2px(10);
    private static final int F_LEFT = (int) Utils.dp2px(4);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean f_shown = false;

    private ObjectAnimator animator;
    private float fraction = 0;

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setTextSize(F_TEXT_SIZE);
        paint.setColor(Color.DKGRAY);
        setPadding(getPaddingLeft(), getPaddingTop() + GAP + F_TEXT_SIZE, getPaddingBottom(), getPaddingBottom());

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (f_shown && TextUtils.isEmpty(s)) {
                    animateHide();
                    f_shown = false;
                } else if (!f_shown && !TextUtils.isEmpty(s)) {
                    animateShow();
                    f_shown = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void animateShow() {
        ObjectAnimator showAnimator = getFloatingAnimator();
        showAnimator.start();
    }

    private void animateHide() {
        ObjectAnimator showAnimator = getFloatingAnimator();
        showAnimator.reverse();
    }

    private ObjectAnimator getFloatingAnimator() {
        if (this.animator == null) {
            this.animator = ObjectAnimator.ofFloat(this, "fraction", 0, 1);
        }
        return this.animator;
    }

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // show: 0 -> 1
        // hide: 1 -> 0
        paint.setAlpha((int) (255 * fraction)); // 255 -- 完全可见； 0 -- 完全不可见

        // show: 0 -> 1, 显示的时候，文字从下方滑入上方, offset是从大逐渐减小
        // hide: 1 -> 0, 隐藏的时候，文字是从上方滑入下方， Offset是逐渐增大的
        float extraOffset = (F_TEXT_SIZE + F_TOP) * fraction;
        canvas.drawText(
                "Username",
                F_LEFT,  // x
                // y: (F_TEXT_SIZE + F_TOP) * 2 隐藏时的最底部,
                // 当show的时候，extraOffset 逐渐增大，则减去之后的值是逐渐减小的
                // 动画效果则是y值逐渐减小，文字从下往上滑动
                // hide的时候，则正好相反
                (F_TEXT_SIZE + F_TOP) * 2 - extraOffset,
                paint);
    }
}
