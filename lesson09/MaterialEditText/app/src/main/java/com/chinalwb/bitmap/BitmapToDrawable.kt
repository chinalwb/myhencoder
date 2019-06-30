package com.chinalwb.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class Util {
    companion object {
        fun getDrawable(bitmap: Bitmap): Drawable {
            return BitmapDrawable(Resources.getSystem(), bitmap)
        }

        fun getBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }

            var bitmap: Bitmap? = null
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888)
            }

            var canvas = Canvas(bitmap)
            drawable.bounds = Rect(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }


}