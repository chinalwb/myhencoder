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
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.chinalwb.scalableimageviewtest.R
import com.chinalwb.scalableimageviewtest.view.Utils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context, attributeSet: AttributeSet) : View(context,
        attributeSet), Runnable {

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
    private var currentScale = 0F
        set(value) {
            field = value
            invalidate()
        }

    var objectAnimator: ObjectAnimator? = null

    private var gestureListener = MyGestureListener()
    var gestureDetector = GestureDetectorCompat(context, gestureListener)

    var offsetX = 0F
    var offsetY = 0F

    var overScroller = OverScroller(context)

    private var scaleGestureListener = MyScaleGestureListener()
    var scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)

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
        currentScale = smallScale

        cx = width / 2F
        cy = height / 2F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.LTGRAY)

        var scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, cx, cy)
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
        var result = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
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


    /**
     * Double Click listener
     */
    inner class MyGestureListener : GestureDetector.OnGestureListener, GestureDetector
    .OnDoubleTapListener {
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
                    -((bWidth * bigScale) - width).toInt() / 2,
                    ((bWidth * bigScale) - width).toInt() / 2,
                    -((bHeight * bigScale) - height).toInt() / 2,
                    ((bHeight * bigScale) - height).toInt() / 2
            )

            postOnAnimation(this@ScalableImageView)
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

            Log.w("XX", "OnDoubleTap")
            if (isBig) {
                // Small to Big
                offsetX = (e!!.x - cx) - (e.x - cx) / smallScale * bigScale
                offsetY = (e.y - cy) - (e.y - cy) / smallScale * bigScale
                fixOffsets()
                getAnimator().start()
            } else {
                // Big to small
                var objectAnimator = getAnimator()
                objectAnimator.reverse()
            }
            return false
        }

        private fun getAnimator(): ObjectAnimator {
            if (objectAnimator == null) {
                objectAnimator = ObjectAnimator.ofFloat(this@ScalableImageView, "currentScale", 0F)
            }

            objectAnimator!!.setFloatValues(smallScale, bigScale)
            return objectAnimator!!
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }

        private fun fixOffsets() {
            offsetX = if (offsetX < 0) {
                max(offsetX, -abs(bWidth * bigScale - width) / 2)
            } else {
                min(offsetX, abs(bWidth * bigScale - width) / 2)
            }

            offsetY = if (offsetY < 0) {
                max(offsetY, -abs(bHeight * bigScale - height) / 2)
            } else {
                min(offsetY, abs(bHeight * bigScale - height) / 2)
            }
        }
    } // #eof MyGestureListener

    /**
     * Scale listener
     */
    inner class MyScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        var initScale = 1F
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initScale = currentScale
            return true
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            currentScale = initScale * detector!!.scaleFactor
            isBig = currentScale > smallScale
            invalidate()
            return false
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }
    }
}