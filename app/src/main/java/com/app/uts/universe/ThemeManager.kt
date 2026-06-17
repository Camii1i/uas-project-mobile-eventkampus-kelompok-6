package com.app.uts.universe

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREF_NAME = "theme_pref"
    private const val KEY_IS_DARK = "is_dark_mode"

    fun isDarkMode(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_IS_DARK, true) // default dark
    }

    fun toggleTheme(activity: Activity) {
        val pref = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = isDarkMode(activity)
        pref.edit().putBoolean(KEY_IS_DARK, !current).apply()
        applyTheme(!current)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun applyTheme(isDark: Boolean) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun applySavedTheme(context: Context) {
        applyTheme(isDarkMode(context))
    }
}