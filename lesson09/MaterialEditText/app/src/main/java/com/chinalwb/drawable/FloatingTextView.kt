package com.chinalwb.drawable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.chinalwb.materialedittext.R
import kotlin.random.Random
import kotlin.random.Random.Default.nextFloat

class FloatingTextView (context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val floatingText: String = "329055754"

    var drawable: MyDrawable = MyDrawable(floatingText)

    val floatingWidth = drawable.paint.measureText(floatingText)

    var bitmap: Bitmap? = null

    var floatingX = 0F
        set(value) {
            field = value
            invalidate()
        }

    var floatingY = 0F

    var objectAnimator: ObjectAnimator? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = loadBitmap(resources, com.chinalwb.materialedittext.R.drawable.rwx, width)
        floatingX = width * 1F
        updateFloatingY()
        Log.w("XX", "onsize changed == " + width)


        objectAnimator = ObjectAnimator.ofFloat(this, "floatingX", 0F - floatingWidth)
                .setDuration(10 * 1000)
        objectAnimator!!.interpolator = LinearInterpolator()
        objectAnimator!!.start()

        objectAnimator!!.repeatMode = ValueAnimator.RESTART
        objectAnimator!!.repeatCount = ValueAnimator.INFINITE
        objectAnimator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator?) {
                updateFloatingY()
                Log.w("XX", "floatingY === " + floatingY + " , animator " + objectAnimator)
            }
        })
    }

    private fun updateFloatingY() {
        floatingY = nextFloat() * height
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(bitmap, 0F, 0F, paint)

        drawable.setBounds(0, 0, width, height)
        drawable.floatingX = floatingX
        drawable.floatingY = floatingY
        drawable.draw(canvas)
    }

    private fun loadBitmap(res: Resources, resId: Int, width: Int): Bitmap {
        var opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, opts)
        opts.inJustDecodeBounds = false
        opts.inDensity = opts.outWidth
        opts.inTargetDensity = width
        return BitmapFactory.decodeResource(res, resId, opts)
    }
}