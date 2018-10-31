package com.chinalwb.lesson07;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chinalwb.lesson07.view.CameraView;

public class MainActivity extends AppCompatActivity {

    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        cameraView = findViewById(R.id.camera_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*
        ObjectAnimator animator = ObjectAnimator.ofInt(cameraView, "bottomRotation", 0, -45);
//        animator.setStartDelay(1000);
//        animator.setDuration(2000);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(10);
//        animator.start();

        ObjectAnimator animator2 = ObjectAnimator.ofInt(cameraView, "canvasRotation", 0, 270);
//        animator2.setStartDelay(1000);
//        animator2.setDuration(2000);
//        animator2.start();
//        animator2.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                animation.setStartDelay(1000);
//                animation.start();
//            }
//        });

        ObjectAnimator animator3 = ObjectAnimator.ofInt(cameraView, "cameraRotation", 0, 45);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator, animator2, animator3);
        set.setStartDelay(1000);
        set.setDuration(2000);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                cameraView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cameraView.reset();
                    }
                }, 1000);
            }
        });
        */
    }
}
