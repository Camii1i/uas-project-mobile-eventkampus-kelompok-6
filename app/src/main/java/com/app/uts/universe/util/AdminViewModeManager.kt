package com.app.uts.universe.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Separate view‑mode manager for the admin UI.
 * Stores the selected mode (list / grid / card) in its own SharedPreferences key
 * so it does not interfere with the student side ViewModeManager.
 */
object AdminViewModeManager {
    const val MODE_LIST = 0
    const val MODE_GRID = 1
    const val MODE_CARD = 2

    private const val PREF_NAME = "admin_view_mode_pref"
    private const val KEY_MODE = "mode"

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getViewMode(context: Context): Int =
        getPrefs(context).getInt(KEY_MODE, MODE_LIST)

    fun setViewMode(context: Context, mode: Int) {
        getPrefs(context).edit().putInt(KEY_MODE, mode).apply()
    }
}
