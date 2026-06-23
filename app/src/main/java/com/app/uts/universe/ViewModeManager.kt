package com.app.uts.universe

import android.content.Context
object ViewModeManager {

    private const val PREF_NAME = "view_mode_pref"
    private const val KEY_VIEW_MODE = "view_mode"

    const val MODE_LIST = 0
    const val MODE_GRID = 1
    const val MODE_CARD = 2

    fun getViewMode(context: Context): Int {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getInt(KEY_VIEW_MODE, MODE_LIST)
    }

    fun setViewMode(context: Context, mode: Int) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putInt(KEY_VIEW_MODE, mode).apply()
    }
}