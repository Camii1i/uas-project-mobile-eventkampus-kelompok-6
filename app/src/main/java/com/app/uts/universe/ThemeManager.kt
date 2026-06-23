package com.app.uts.universe

import android.content.Context
import android.app.Activity
import com.app.uts.universe.R
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREF_NAME = "theme_pref"
    private const val KEY_IS_DARK = "is_dark_mode"

    fun isDarkMode(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_IS_DARK, true)
    }

    fun toggleTheme(activity: Activity) {
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        val pref = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val newTheme = !isDarkMode(activity)

        pref.edit()
            .putBoolean(KEY_IS_DARK, newTheme)
            .apply()

        applyTheme(newTheme)
    }

    fun applyTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun applySavedTheme(context: Context) {
        applyTheme(isDarkMode(context))
    }
}