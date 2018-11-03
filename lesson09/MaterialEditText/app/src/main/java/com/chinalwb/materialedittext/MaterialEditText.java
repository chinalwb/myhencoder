package com.chinalwb.materialedittext;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
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
    private int originalPaddingTop = -1;
    private int originalPaddingBottom = -1;
    private int originalPaddingLeft = -1;
    private int originalPaddingRight = -1;

    private int width, height;

    private boolean useFloatingLabel = false;

    // -------- Text counting part --------- //

    // Text counting height
    private static final int C_HEIGHT = (int) Utils.dp2px(15);
    // The gap between cursor and the underline
    private static final int CURSOR_UNDERLINE_GAP = (int) Utils.dp2px(2);
    private static final int UNDERLINE_HEIGHT = (int) Utils.dp2px(2);
    private static final int UNDERLINE_COUNTING_GAP = (int) Utils.dp2px(2);
    private static final int COUNTING_TEXT_SIZE = (int) Utils.dp2px(14);
    private boolean useTextCounting = true;
    private Rect underlineRect = new Rect(); // should be created on demand
    private int maxLength = 10;
    private Rect countingTextRect = new Rect(); // should be created on demand

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, false);

        init();
    }

    private void init() {
        if (this.originalPaddingLeft < 0) {
            setOriginalPaddingLeft(getPaddingLeft());
        }
        if (this.originalPaddingTop < 0) {
            setOriginalPaddingTop(getPaddingTop());
        }
        if (this.originalPaddingRight < 0) {
            setOriginalPaddingRight(getPaddingRight());
        }
        if (this.originalPaddingBottom < 0) {
            setOriginalPaddingBottom(getPaddingBottom());
        }


        setPaddingForFloatingLabel();
        paint.setTextSize(F_TEXT_SIZE);
        paint.setColor(Color.DKGRAY);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateFloatingText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.width = w;
        this.height = h;


        underlineRect.left = this.originalPaddingLeft;
        underlineRect.right = this.width - this.originalPaddingRight;
    }

    private void updateFloatingText(CharSequence s) {
        if (useFloatingLabel) {
            if (f_shown && TextUtils.isEmpty(s)) {
                animateHide();
                f_shown = false;
            } else if (!f_shown && !TextUtils.isEmpty(s)) {
                animateShow();
                f_shown = true;
            }
        }
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

    public boolean isUseFloatingLabel() {
        return useFloatingLabel;
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel;
            setPaddingForFloatingLabel();
            updateFloatingText(getText());
        }
    }

    private void setPaddingForFloatingLabel() {
        int paddingTop = getOriginalPaddingTop();
        if (useFloatingLabel) {
            paddingTop = paddingTop + GAP + F_TEXT_SIZE;
        }
        int paddingBottom = getOriginalPaddingBottom();
        if (useTextCounting) {
            paddingBottom = paddingBottom + C_HEIGHT;
        }
        setPadding(getPaddingLeft(), paddingTop, getPaddingBottom(), paddingBottom);
    }

    public int getOriginalPaddingTop() {
        return originalPaddingTop;
    }

    public void setOriginalPaddingTop(int originalPaddingTop) {
        this.originalPaddingTop = originalPaddingTop;
    }

    public int getOriginalPaddingBottom() {
        return originalPaddingBottom;
    }

    public void setOriginalPaddingBottom(int originalPaddingBottom) {
        this.originalPaddingBottom = originalPaddingBottom;
    }

    public int getOriginalPaddingLeft() {
        return originalPaddingLeft;
    }

    public void setOriginalPaddingLeft(int originalPaddingLeft) {
        this.originalPaddingLeft = originalPaddingLeft;
    }

    public int getOriginalPaddingRight() {
        return originalPaddingRight;
    }

    public void setOriginalPaddingRight(int originalPaddingRight) {
        this.originalPaddingRight = originalPaddingRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (useTextCounting) {
            setBackgroundDrawable(null);
            paint.setColor(Color.BLUE);
            underlineRect.top = getScrollY() + this.height - C_HEIGHT - originalPaddingBottom + CURSOR_UNDERLINE_GAP;
            underlineRect.bottom = underlineRect.top + UNDERLINE_HEIGHT;
            canvas.drawRect(underlineRect, paint);

            paint.setTextSize(COUNTING_TEXT_SIZE);
            int currentLen = getText().length();
            String text = currentLen + "/" + this.maxLength;
            if (currentLen > this.maxLength) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.BLUE);
            }
            paint.getTextBounds(text, 0, text.length(), countingTextRect);
            float x = this.width - countingTextRect.width() - this.originalPaddingRight;
            float y = underlineRect.bottom + countingTextRect.height() + UNDERLINE_COUNTING_GAP;
            canvas.drawText(text, x, y, paint);
        }

        paint.setColor(Color.DKGRAY);
        if (useFloatingLabel) {
            // show: 0 -> 1
            // hide: 1 -> 0
            paint.setAlpha((int) (255 * fraction)); // 255 -- 完全可见； 0 -- 完全不可见

            // show: 0 -> 1, 显示的时候，文字从下方滑入上方, offset是从大逐渐减小
            // hide: 1 -> 0, 隐藏的时候，文字是从上方滑入下方， Offset是逐渐增大的
            float extraOffset = (F_TEXT_SIZE + F_TOP) * fraction;
            canvas.drawText(
                    getHint() != null ? getHint().toString() : "",
                    F_LEFT,  // x
                    // y: (F_TEXT_SIZE + F_TOP) * 2 隐藏时的最底部,
                    // 当show的时候，extraOffset 逐渐增大，则减去之后的值是逐渐减小的
                    // 动画效果则是y值逐渐减小，文字从下往上滑动
                    // hide的时候，则正好相反
                    (F_TEXT_SIZE + F_TOP) * 2 - extraOffset,
                    paint);
        }

    }
}
