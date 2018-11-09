package com.chinalwb.scalableimageviewtest.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;


public class Utils {

    public static final float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static final Bitmap getBitmap(Resources res, int resId, int targetWidth) {
        int sampleSize = 1;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res, resId, options);
        int srcWidth = options.outWidth;

        while ((float) srcWidth / targetWidth > 1.4f) {
            sampleSize *= 2;
            srcWidth /= 2;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inScaled = true;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth * sampleSize;

        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;
    }
}
