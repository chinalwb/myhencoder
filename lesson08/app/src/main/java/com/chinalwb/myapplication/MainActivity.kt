package com.chinalwb.myapplication

import android.animation.*
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private var mImageView: ImageView? = null

    private var circleView: CircleView? = null

    private var cameraView: CameraView? = null

    private var cameraViewRight: CameraViewRight? = null

    private var pointView: PointView? = null

    private var stringView: StringView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
//        testViewPropertyAnimation()
//        testObjectAnimator()
//        testCameraView()
//        testCameraViewRight()
//        testPropertyValuesHolder()
//        testTypeEvaluator()
        testStringViewAnimator()
    }

    private fun init() {
//        mImageView = findViewById(R.id.image)
//        circleView = findViewById(R.id.circle_view)
//        cameraView = findViewById(R.id.camera_view)
//        cameraViewRight = findViewById(R.id.camera_view_right)
//        pointView = findViewById(R.id.point)
        stringView = findViewById(R.id.string_view)
    }

    private fun testStringViewAnimator() {
        var animator = ObjectAnimator.ofObject(
            stringView,
            "index",
            object : TypeEvaluator<Int> {
                override fun evaluate(fraction: Float, startValue: Int?, endValue: Int?): Int {
                    println("start valule >> " + startValue)
                    return startValue!! + ((endValue!! - startValue) * fraction).toInt()
                }
            },
            StringView.strings.size - 1
        )

        animator.interpolator = AccelerateInterpolator()

        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()
    }

    private fun testTypeEvaluator() {
        val targetPoint = Point(
            Util.dp2px(300).toInt(),
            Util.dp2px(300).toInt()
        )
        var animator = ObjectAnimator.ofObject(
            pointView, "point",
            PointTypeEvaluator(), targetPoint
        )
        animator.duration = 1000
        animator.start()
    }

//    private fun testPropertyValuesHolder() {
//        var max = Util().dp2px(300)
//        var keyframe1 = Keyframe.ofFloat(0F, 0F)
//        var keyframe2 = Keyframe.ofFloat(0.2F, 0.1F * max)
//        var keyframe3 = Keyframe.ofFloat(0.9F, 0.5F * max)
//        var keyframe4 = Keyframe.ofFloat(1F, max)
//        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4)
//        val animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, propertyValuesHolder)
//        animator.duration = 2000
//        animator.startDelay = 1000
//        animator.start()
//    }

    private fun testCameraViewRight() {
//        var animator1 = ObjectAnimator.ofFloat(cameraViewRight!!, "rightFlip", -45F)
//        animator1.duration = 1000
//
//        var animator2 = ObjectAnimator.ofFloat(cameraViewRight!!, "canvasRotation", 270F)
//        animator2.duration = 1000
//
//        var animator3 = ObjectAnimator.ofFloat(cameraViewRight!!, "leftFlip", 45F)
//        animator3.duration = 1000
//
//        var animatorSet = AnimatorSet()
//        animatorSet.playSequentially(animator1, animator2, animator3)
//        animatorSet.startDelay = 1000
//        animatorSet.start()

        var h1 = PropertyValuesHolder.ofFloat("rightFlip", -45F)
        var h2 = PropertyValuesHolder.ofFloat("canvasRotation", 270F)
        var h3 = PropertyValuesHolder.ofFloat("leftFlip", 45F)
        val animator = ObjectAnimator.ofPropertyValuesHolder(cameraViewRight!!, h1, h2, h3)
        animator.duration = 1000
        animator.startDelay = 1000
        animator.start()

    }

//    private fun testCameraView() {
//        var animator1 = ObjectAnimator.ofFloat(cameraView!!, "bottomFlip", 45F)
//        animator1.duration = 1000
//        var animator2 = ObjectAnimator.ofFloat(cameraView!!, "canvasRotate", 270F)
//        animator2.duration = 1000
//        var animator3 = ObjectAnimator.ofFloat(cameraView!!, "topFlip", -45F)
//        animator3.duration = 1000
//
//        var animatorSet = AnimatorSet()
//        animatorSet.startDelay = 1000
//        animatorSet.playSequentially(animator1, animator2, animator3)
//        animatorSet.start()
//    }

//    private fun testObjectAnimator() {
//        var animator = ObjectAnimator.ofFloat(circleView!!, "radius", 200F)
//        animator.duration = 1000
//        animator.startDelay = 1000
//        animator.start()
//    }

//    private fun testViewPropertyAnimation() {
//        var animator1 = ObjectAnimator.ofFloat(mImageView!!, "translationX", 500F)
//        animator1.duration = 1000
//        var animator2 = ObjectAnimator.ofFloat(mImageView!!, "rotation", 360F)
//        animator2.duration = 1000
//
//        var animator3 = ObjectAnimator.ofFloat(mImageView!!, "alpha", 0F)
//        var animator4 = ObjectAnimator.ofFloat(mImageView!!, "alpha", 1F)
//
//        var set1 = AnimatorSet()
//        set1.playSequentially(animator3, animator4)
//        set1.duration = 1000
//
//        var animator5 = ObjectAnimator.ofFloat(mImageView!!, "translationY", 400F)
//        animator5.duration = 2000
//
//        var animatorSet = AnimatorSet()
//        animatorSet.playSequentially(animator1, animator2, animator5)
//        animatorSet.playTogether(set1)
//        animatorSet.start()
//    }

    class PointTypeEvaluator : TypeEvaluator<Point> {
        override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {
            Log.w("XX", "start value $startValue")
            return Point(
                startValue!!.x + ((endValue!!.x - startValue.x) * fraction).toInt(),
                startValue!!.y + ((endValue.y - startValue.y) * fraction).toInt()
            )
        }
    }
}
