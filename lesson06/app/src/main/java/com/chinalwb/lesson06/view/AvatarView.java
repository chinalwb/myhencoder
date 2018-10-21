package com.chinalwb.lesson06.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson06.R;
import com.chinalwb.lesson06.Utils;

public class AvatarView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width, height, left, top, right, bottom, cx, cy, radius;
    private int padding = (int) Utils.dp2px(50);
    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.left = padding;
        this.top = padding;
        this.width = w - padding * 2;
        this.cx = this.left + this.width / 2;
        this.cy = this.top + this.width / 2;
        this.radius = this.width / 2;
        this.right = this.left + this.width;
        this.bottom = this.top + this.width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.addCircle(cx, cy, radius , Path.Direction.CCW);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);

        int saveCount = canvas.saveLayer(left, top, right, bottom, paint);
        path.reset();
        path.addCircle(cx, cy, radius - radius / 15 , Path.Direction.CCW);
        canvas.drawPath(path, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Bitmap bitmap = getAvatar(this.width);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restoreToCount(saveCount);

        // 圆形图片 1
//        canvas.save();
//        Path path = new Path();
//        path.addCircle(cx, cy, radius, Path.Direction.CCW);
//        canvas.clipPath(path);
//        Bitmap bitmap = getAvatar(this.width);
//        canvas.drawBitmap(bitmap, left, top, paint);
//        canvas.restore();
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.rengwuxian, options);
    }
}
