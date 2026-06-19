package com.app.uts.universe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DURATION = 1800L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Animasi fade-in
        val root = findViewById<View>(R.id.splashRoot)
        root.animate()
            .alpha(1f)
            .setDuration(600)
            .start()

        // Navigasi setelah delay
        lifecycleScope.launch {
            delay(SPLASH_DURATION)
            navigateToNext()
        }
    }

    private fun navigateToNext() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}