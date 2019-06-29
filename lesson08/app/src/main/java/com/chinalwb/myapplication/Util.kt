package com.chinalwb.myapplication

import android.content.res.Resources

class Util {
    companion object {
        fun dp2px(dp: Int): Float = Resources.getSystem().displayMetrics.density * dp
    }
}