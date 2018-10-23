package com.chinalwb.testlesson06.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.testlesson06.R;
import com.chinalwb.testlesson06.Utils;

public class AvatarView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width, height, cx, cy, radius;
    private float padding = Utils.dp2px(10);
    private RectF bounds;
    private int borderWidth = (int) Utils.dp2px(5);

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(Utils.dp2px(1));
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.cx = this.width / 2;
        this.cy = this.width / 2;
        this.radius = this.width / 2 - (int) padding;

        this.bounds = new RectF(0, 0, this.width, this.width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(cx,cy,radius,paint);
        int savedLayer = canvas.saveLayer(this.bounds, paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(cx,cy,radius - borderWidth,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap bitmap = getBitmap();
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(savedLayer);
    }

    private Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.rengwuxian, options);
        options.inDensity = options.outWidth;
        options.inTargetDensity = this.width;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rengwuxian, options);
        return bitmap;
    }
}
