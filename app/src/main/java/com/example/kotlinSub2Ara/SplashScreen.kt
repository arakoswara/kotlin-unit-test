package com.example.kotlinSub2Ara

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.BounceInterpolator

class SplashScreen : AppCompatActivity() {
    private val ANIMATION_DURATION : Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimation()
    }

    private fun startAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.interpolator = BounceInterpolator() as TimeInterpolator?
        valueAnimator.duration = ANIMATION_DURATION

        // Set animator listener.
        val intent = Intent(this, MainActivity::class.java)
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}

        })

        // Start animation.
        valueAnimator.start()
    }
}
