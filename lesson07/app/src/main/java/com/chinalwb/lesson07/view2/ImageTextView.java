package com.chinalwb.lesson07.view2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chinalwb.lesson07.R;
import com.chinalwb.lesson07.Utils;

public class ImageTextView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    private int width;

    private Bitmap bitmap;


    public ImageTextView(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();

        textPaint.setTextSize(Utils.dp2px(12));
        paint.setTextSize(Utils.dp2px(12));

        bitmap = Utils.loadBitmap(getResources(), R.drawable.car, (int) Utils.dp2px(200));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        String text = "Regarding the ACL propagation, it is obviously simpler to make it one way." +
                " One thing to consider, though, is the possibility for Vmoso users to share beyond the settings propagated by the VIB, and the potential for the VIB to spare those sharings when doing the propagation. That could be achieved by using distinct roles - some managed by the VIB and some not.";

//        StaticLayout staticLayout = new StaticLayout(
//                "Regarding the ACL propagation, it is obviously simpler to make it one way. One thing to consider, though, is the possibility for Vmoso users to share beyond the settings propagated by the VIB, and the potential for the VIB to spare those sharings when doing the propagation. That could be achieved by using distinct roles - some managed by the VIB and some not.",
//                textPaint,
//                width,
//                Layout.Alignment
//                .ALIGN_NORMAL,
//                1,
//                2,
//                false);
//        staticLayout.draw(canvas);

        int bitmapTop = 10;
        canvas.drawBitmap(bitmap, 0, bitmapTop, paint);

        float nextLineY = - paint.getFontMetrics().top;
        float[] measuredWidth = {};
        int startIndex = 0;
        int measuredLength = 0;
        int textLength = text.length();
        float fontSpacing = paint.getFontSpacing();

        int bitmapBottom = bitmapTop + bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        int gap = 10;
        int x = 0;
        int tolerance = 10;
        int maxWidth = width - bitmapWidth - gap;

        Log.e("xx", "bitmapBottom == " + bitmapBottom);
        Log.e("xx", "fontSpacing == " + fontSpacing);
        while (startIndex < textLength) {
            Log.e("xx", "nextLineY == " + nextLineY);
            if (nextLineY > bitmapTop && nextLineY - fontSpacing + tolerance < bitmapBottom) {
                x = bitmapWidth + gap;
                maxWidth = width - bitmapWidth - gap;
            } else {
                x = gap;
                maxWidth = width - gap;
            }
            if (nextLineY > bitmapBottom && nextLineY - fontSpacing < bitmapBottom) {
                canvas.drawLine(0, (nextLineY - fontSpacing), getWidth(), (nextLineY -
                        fontSpacing), paint);
            }
            measuredLength = paint.breakText(
                    text, startIndex, textLength, true, maxWidth, measuredWidth
            );
            canvas.drawText(text, startIndex, startIndex + measuredLength, x, nextLineY, paint);
            startIndex += measuredLength;
            nextLineY += fontSpacing;
        }
    }
}
