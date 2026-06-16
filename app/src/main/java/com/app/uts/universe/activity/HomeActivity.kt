package com.app.uts.universe.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager  // ← TAMBAH
import com.app.uts.universe.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var btnLogout: Button
    private lateinit var btnToggleTheme: Button  // ← TAMBAH
    private lateinit var tvUsername: TextView

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        username = intent.getStringExtra("username") ?: ""

        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        btnLogout = findViewById(R.id.btnLogout)
        btnToggleTheme = findViewById(R.id.btnToggleTheme)
        tvUsername = findViewById(R.id.tvUsername)

        tvUsername.text = username

        updateThemeButton()

        btnToggleTheme.setOnClickListener {
            ThemeManager.toggleTheme(this)
        }

        viewPager.adapter = ViewPagerAdapter(this, username)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> viewPager.currentItem = 0
                R.id.nav_riwayat -> viewPager.currentItem = 1
            }
            true
        }

        if (intent.getBooleanExtra("openRiwayat", false)) {
            viewPager.currentItem = 1
        }

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun updateThemeButton() {
        if (ThemeManager.isDarkMode(this)) {
            btnToggleTheme.text = "☀️"
        } else {
            btnToggleTheme.text = "🌙"
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ -> logout() }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}