package com.chinalwb.lesson07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chinalwb.lesson07.R;
import com.chinalwb.lesson07.Utils;

public class CameraView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float cx, cy;
    private Camera camera;
    private int canvasRotation = 0;
    private int cameraRotation = 0;
    private int bottomRotation = 0;


    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.bitmap = Utils.loadBitmap(getResources(), R.drawable.rengwuxian, (int) Utils.dp2px
                (300));
        this.camera = new Camera();
        this.camera.setLocation(0,0, -8 * getResources().getDisplayMetrics().density);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.cx = w / 2;
        this.cy = h / 2;
    }

    public void reset() {
        this.cameraRotation = 0;
        this.canvasRotation = 0;
        this.bottomRotation = 0;
        this.invalidate();
    }

    public int getCanvasRotation() {
        return canvasRotation;
    }

    public void setCanvasRotation(int canvasRotation) {
        this.canvasRotation = canvasRotation;
        invalidate();
    }

    public int getCameraRotation() {
        return cameraRotation;
    }

    public void setCameraRotation(int cameraRotation) {
        this.cameraRotation = cameraRotation;
        invalidate();
    }

    public int getBottomRotation() {
        return bottomRotation;
    }

    public void setBottomRotation(int bottomRotation) {
        this.bottomRotation = bottomRotation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left = cx - bitmap.getWidth() / 2;
        float top = cy - bitmap.getHeight() / 2;
        float right = left + bitmap.getWidth();
        float bottom = top + bitmap.getHeight();

        // With 3D rotation
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-canvasRotation);
        this.camera.save();
        this.camera.rotateY(this.cameraRotation);
        this.camera.applyToCanvas(canvas);
        this.camera.restore();
        canvas.clipRect(-cx * 2, -cy, 0, getHeight());
        canvas.rotate(canvasRotation);
        canvas.translate(-cx, -cy);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-canvasRotation);
        this.camera.save();
        this.camera.rotateY(this.bottomRotation);
        this.camera.applyToCanvas(canvas);
        this.camera.restore();
        canvas.clipRect(0, -cy * 2, getWidth(), getHeight());
        canvas.rotate(canvasRotation);
        canvas.translate(-cx, -cy);
        canvas.drawBitmap(bitmap, cx - bitmap.getWidth() / 2, cy - bitmap.getHeight() / 2, paint);
        canvas.restore();
    }


}
