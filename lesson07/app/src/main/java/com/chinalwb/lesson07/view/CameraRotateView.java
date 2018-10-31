package com.chinalwb.lesson07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinalwb.lesson07.R;
import com.chinalwb.lesson07.Utils;

public class CameraRotateView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private int width, height, cx, cy;
    private Camera camera;

    public CameraRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setTextSize(100);
        bitmap = Utils.loadBitmap(getResources(), R.drawable.rengwuxian, (int) Utils.dp2px(300));
        camera = new Camera();
        camera.setLocation(0,0, -8 * getResources().getDisplayMetrics().density);
        camera.rotateX(45);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.cx = w / 2;
        this.cy = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();

        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-20);
        canvas.clipRect(-cx, -cy, cx, 0);
        canvas.rotate(20);
        canvas.translate(-cx, -cy);
        canvas.drawColor(Color.MAGENTA);
        canvas.drawBitmap(bitmap, cx - bw / 2, cy - bh / 2, paint);
        canvas.restore();

        //
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-20);
        camera.applyToCanvas(canvas);

        canvas.clipRect(-cx, 0, cx, cy);
        canvas.rotate(20);

        canvas.translate(-cx, -cy);
        canvas.drawColor(Color.GRAY);
        canvas.drawBitmap(bitmap, cx - bw / 2, cy - bh / 2, paint);
        canvas.drawText("AAAA", 0, cy + 100, paint);
        canvas.restore();
    }
}
