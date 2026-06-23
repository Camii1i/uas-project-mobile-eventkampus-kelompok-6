package com.app.uts.universe.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import com.app.uts.universe.adapter.AdminViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class AdminActivity : AppCompatActivity() {

    private lateinit var viewPagerAdmin: ViewPager2
    private lateinit var bottomNavigationAdmin: BottomNavigationView
    private lateinit var btnLogout: Button
    private lateinit var btnProfileAdmin: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        viewPagerAdmin       = findViewById(R.id.viewPagerAdmin)
        bottomNavigationAdmin = findViewById(R.id.bottomNavigationAdmin)
        btnLogout            = findViewById(R.id.btnLogout)
        btnProfileAdmin      = findViewById(R.id.btnProfileAdmin)

        viewPagerAdmin.adapter = AdminViewPagerAdapter(this)

        viewPagerAdmin.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigationAdmin.menu.getItem(position).isChecked = true
            }
        })

        bottomNavigationAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard  -> viewPagerAdmin.currentItem = 0
                R.id.nav_create     -> viewPagerAdmin.currentItem = 1
                R.id.nav_mahasiswa  -> viewPagerAdmin.currentItem = 2
            }
            true
        }

        btnLogout.setOnClickListener { showLogoutDialog() }

        btnProfileAdmin.setOnClickListener {
            startActivity(Intent(this, ProfileAdminActivity::class.java))
        }
    }

    fun goToCreateTab() {
        viewPagerAdmin.currentItem = 1
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ -> logout() }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
