package com.app.uts.universe.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val centerGroup = findViewById<View>(R.id.centerGroup)
        val bottomGroup = findViewById<View>(R.id.bottomGroup)
        val progressBar = findViewById<View>(R.id.progressBar)

        // Animate centerGroup: fade-in + slide-up (500ms)
        centerGroup.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .start()

        // Animate bottomGroup: fade-in (400ms with 300ms delay)
        bottomGroup.animate()
            .alpha(1f)
            .setDuration(400)
            .setStartDelay(300)
            .start()

        // Animate horizontal progress bar: 0 → 100% over 2000ms (linear)
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animator.setDuration(2000)
        animator.setInterpolator(LinearInterpolator())
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                navigateToNext()
            }
        })
        animator.start()
    }

    private fun navigateToNext() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
