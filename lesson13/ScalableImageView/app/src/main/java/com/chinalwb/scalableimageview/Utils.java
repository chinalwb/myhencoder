package com.chinalwb.scalableimageview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

public class Utils {

    public static final float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getBitmap(Resources res, int resId, int targetWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        int sampleSize = 1;
        float sourceWidth = options.outWidth;
        while (sourceWidth / targetWidth > 1f) {
            sourceWidth /= 2;
            sampleSize *= 2;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth * sampleSize;

        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;
    }
}
