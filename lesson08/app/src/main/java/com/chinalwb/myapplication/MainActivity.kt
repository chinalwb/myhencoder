package com.chinalwb.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private var mImageView: ImageView? = null

    private var circleView: CircleView? = null

    private var cameraView: CameraView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
//        testViewPropertyAnimation()
//        testObjectAnimator()
        testCameraView()
    }

    private fun init() {
//        mImageView = findViewById(R.id.image)
//        circleView = findViewById(R.id.circle_view)
        cameraView = findViewById(R.id.camera_view)
    }

    private fun testCameraView() {
        var animator1 = ObjectAnimator.ofFloat(cameraView!!, "bottomFlip", 45F)
        animator1.duration = 1000
        var animator2 = ObjectAnimator.ofFloat(cameraView!!, "canvasRotate", 270F)
        animator2.duration = 1000
        var animator3 = ObjectAnimator.ofFloat(cameraView!!, "topFlip", -45F)
        animator3.duration = 1000

        var animatorSet = AnimatorSet()
        animatorSet.startDelay = 1000
        animatorSet.playSequentially(animator1, animator2, animator3)
        animatorSet.start()
    }

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
}
