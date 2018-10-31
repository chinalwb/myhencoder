package com.chinalwb.lesson07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson07.R;
import com.chinalwb.lesson07.Utils;

public class ImageTextView extends View {

    private static final String text = "言下之意是：像窃听特朗普这样的高级机密行动(a highly classified operation)" +
            "先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性!" +
            "言下之意是：像窃听特朗普这样的高级机密行动(a highly classified operation)" +
            "先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性" +
            "先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性" +
            "言下之意是：像窃听特朗普这样的高级机密行动(a highly classified operation)先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性先是捕风捉影、炮制假新闻；被怼之后又用各种添油加醋、写新闻报道仿佛在写小说、毫无半点专业性";
    private Paint paint;
    private float[] measuredWidth = new float[2];
    private int width;
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private int bitmapLeft, bitmapRight, bitmapTop, bitmapBottom;
    private Rect bounds = new Rect();

    private static final int TEXT_LEFT = (int) Utils.dp2px(2);
    private static final int FIRST_LINE_TOP = (int) Utils.dp2px(5);


    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(Utils.dp2px(12));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = Utils.loadBitmap(getResources(), R.drawable.rengwuxian, (int) Utils.dp2px(100));
        this.bitmapLeft = getWidth() - bitmap.getWidth();
        this.bitmapTop = 100;
        this.bitmapRight = bitmapLeft + bitmap.getWidth();
        this.bitmapBottom = bitmapTop + bitmap.getHeight();
        canvas.drawBitmap(bitmap, bitmapLeft, bitmapTop, paint);

        paint.getFontMetrics(fontMetrics);
        float fontHeight = fontMetrics.leading - fontMetrics.top;

        int lengthTmp = 0;
        int topTmp = (int) (fontHeight + FIRST_LINE_TOP);
        int line = 0;
        int totalLen = text.length();
        while (lengthTmp < text.length()) {
            int index = paint.breakText(text, lengthTmp, totalLen, true, this.width, measuredWidth);
            paint.getTextBounds(text, lengthTmp, lengthTmp + index, bounds);
            float baseY = topTmp + paint.getFontSpacing() * line;
            if (bounds.bottom + baseY < bitmapTop || bounds.top + baseY > bitmapBottom) {
                // No overlap
            } else {
                // Have overlap between text and image
                // Draw left part
                index = paint.breakText(text, lengthTmp, totalLen, true, this.width - bitmap
                        .getWidth(), measuredWidth);
            }
            canvas.drawText(
                    text,
                    lengthTmp,
                    lengthTmp + index,
                    TEXT_LEFT,
                    topTmp + paint.getFontSpacing() * line,
                    paint
            );
            line++;
            lengthTmp += index;
        }


    }
}
