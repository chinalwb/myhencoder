package com.chinalwb.scalableimageviewtest.view2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import com.chinalwb.scalableimageviewtest.R
import com.chinalwb.scalableimageviewtest.view.Utils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context, attributeSet: AttributeSet) : View(context,
        attributeSet), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
        Runnable {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bitmap: Bitmap? = null
    var bWidth = 0
    var bHeight = 0
    var bLeft = 0F
    var bTop = 0F
    var smallScale = 1F
    var bigScale = 1F
    private val overScale = 2F
    private var cx = 0F
    var cy = 0F
    var isBig = false
    private var scaleFraction = 0F
        set(value) {
            field = value
            invalidate()
        }

    var gestureDetector = GestureDetectorCompat(context, this)

    var offsetX = 0F
    var offsetY = 0F

    var overScroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = loadResource(R.drawable.s, Utils.dp2px(300).toInt())

        bWidth = bitmap!!.width
        bHeight = bitmap!!.height

        bLeft = (width - bWidth) / 2F
        bTop = (height - bHeight) / 2F

        smallScale = if (bWidth >= bHeight) width / bWidth.toFloat() else height / bHeight.toFloat()
        bigScale = if (bWidth >= bHeight) height / bHeight.toFloat() else width / bWidth.toFloat()
        bigScale *= overScale

        cx = width / 2F
        cy = height / 2F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.LTGRAY)

        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        var scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(scale, scale, cx, cy)
        canvas.drawBitmap(bitmap!!, bLeft, this.bTop, paint)
    }

    private fun loadResource(resId: Int, targetWidth: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = targetWidth

        return BitmapFactory.decodeResource(resources, resId, options)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onFling(down: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float):
            Boolean {
        if (!isBig) return false

        overScroller.fling(
                offsetX.toInt(),
                offsetY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                - ((bWidth * bigScale) - width).toInt() / 2,
                ((bWidth * bigScale) - width).toInt() / 2,
                - ((bHeight * bigScale) - height).toInt() / 2,
                ((bHeight * bigScale) - height).toInt() / 2
        )

        postOnAnimation(this)
        return false
    }

    override fun onScroll(down: MotionEvent?, event: MotionEvent?, distanceX: Float, distanceY:
    Float): Boolean {
        if (!isBig) return false

        offsetX -= distanceX
        offsetY -= distanceY
        fixOffsets()
        invalidate()
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }


    // --- OnDoubleTapListener callbacks

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isBig = !isBig

        if (isBig) {
            // Small to Big
            offsetX = (e!!.x - cx) - (e.x - cx) / smallScale * bigScale
            offsetY = (e!!.y - cy) - (e.y - cy) / smallScale * bigScale
            fixOffsets()
            getAnimator().start()
        } else {
            // Big to small
            var objectAnimator = getAnimator()
            objectAnimator.addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    offsetX = 0F
                    offsetY = 0F
                }
            })
            objectAnimator.reverse()
        }
        return false
    }

    private fun getAnimator(): ObjectAnimator {
        return ObjectAnimator.ofFloat(this, "scaleFraction", 0F, 1F)
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    private fun fixOffsets() {
        offsetX = if (offsetX < 0) {
            max(offsetX, - abs(bWidth * bigScale - width) / 2)
        } else {
            min(offsetX, abs(bWidth * bigScale - width) / 2)
        }

        offsetY = if (offsetY < 0) {
            max(offsetY, - abs(bHeight * bigScale - height) / 2)
        } else {
            min(offsetY, abs(bHeight * bigScale - height) / 2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            invalidate()
            postOnAnimation(this)
        }
    }
}