package com.chinalwb.scalableimageview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;

import javax.crypto.spec.OAEPParameterSpec;
import javax.security.auth.login.LoginException;

public class Utils {

    public static float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getBitmap(Resources resources, int resId, int targetWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        int inSampleSize = 1;
        int width = options.outWidth;
        while (width / targetWidth > 2) {
            inSampleSize *= 2;
            width = width / 2;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        options.inScaled = true;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth * inSampleSize;

        Log.e("XX", "000 -->> Src width == " + options.outWidth + ", targetWidth == " + targetWidth + ", inSampleSize = " + inSampleSize);

        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId, options);
        int w = options.outWidth;
        int h = options.outHeight;
        Log.e("XX", "111 --->> decode: width == " + w + ", height == " + h);
        w = bitmap.getWidth();
        h = bitmap.getHeight();
        Log.e("XX", "222 --->> bitmap width == " + w + ", height == " + h);
        return bitmap;
    }

    private static final int calculateInSampleSize(Resources res, int resId, int targetWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        int sampleSize = 1;
        int width = options.outWidth;
        while (width / targetWidth > 2) {
            sampleSize *= 2;
            width = width / 2;
        }
        return sampleSize;
    }
}
