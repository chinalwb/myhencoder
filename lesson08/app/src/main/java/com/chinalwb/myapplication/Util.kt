package com.chinalwb.myapplication

import android.content.res.Resources

class Util {
    fun dp2px(dp: Int): Float {
        return Resources.getSystem().displayMetrics.density * dp
    }
}