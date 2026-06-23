package com.app.uts.universe.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import com.app.uts.universe.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var btnToggleTheme: Button
    private lateinit var tvUsername: TextView
    private lateinit var btnProfile: MaterialCardView
    private lateinit var tvInitial: TextView

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        username = intent.getStringExtra("username") ?: ""

        initViews()
        setupListeners()
        loadUserData()
        updateThemeButton()

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
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        btnToggleTheme = findViewById(R.id.btnToggleTheme)
        tvUsername = findViewById(R.id.tvUsername)
        btnProfile = findViewById(R.id.btnProfile)
        tvInitial = findViewById(R.id.tvInitial)
    }

    private fun setupListeners() {
        btnToggleTheme.setOnClickListener {
            ThemeManager.toggleTheme(this)
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileMahasiswaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserData() {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("nama", null)
        
        tvUsername.text = nama ?: username
        
        val initialSource = nama ?: username
        if (initialSource.isNotEmpty()) {
            tvInitial.text = initialSource[0].uppercaseChar().toString()
        }
    }

    private fun updateThemeButton() {
        if (ThemeManager.isDarkMode(this)) {
            btnToggleTheme.text = "☀️"
        } else {
            btnToggleTheme.text = "🌙"
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }
}