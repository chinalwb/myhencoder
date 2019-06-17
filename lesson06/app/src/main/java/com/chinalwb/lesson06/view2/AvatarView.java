package com.chinalwb.lesson06.view2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.R;
import com.chinalwb.lesson06.Utils;

public class AvatarView extends View {
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float IMAGE_WIDTH = Utils.dp2px(280);
    private int cx, cy;

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = getWidth() / 2;
        cy = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap avatar = getAvatar((int) IMAGE_WIDTH);
        float left = cx - IMAGE_WIDTH / 2;
        float top = cy - IMAGE_WIDTH / 2;

        RectF bounds = new RectF();
        bounds.left = left;
        bounds.top = top;
        bounds.right = left + IMAGE_WIDTH;
        bounds.bottom = top + IMAGE_WIDTH;
        canvas.drawCircle(cx, cy, IMAGE_WIDTH / 2 + Utils.dp2px(5), paint);

        int savedLayer = canvas.saveLayer(bounds, paint);
        canvas.drawCircle(cx, cy, IMAGE_WIDTH / 2, paint); // dst
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(avatar, left, top, paint); // src
        paint.setXfermode(null);
        canvas.restoreToCount(savedLayer);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.rengwuxian, options);
    }
}
