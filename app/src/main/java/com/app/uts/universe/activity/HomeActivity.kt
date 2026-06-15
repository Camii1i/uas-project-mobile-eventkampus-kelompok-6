package com.app.uts.universe.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.adapter.MahasiswaEventAdapter
import com.app.uts.universe.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var rvMahasiswa: RecyclerView
    private lateinit var btnLogout: Button
    private lateinit var tvUsername: TextView
    private lateinit var bottomNavigation: BottomNavigationView

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        username = intent.getStringExtra("username") ?: ""

        rvMahasiswa = findViewById(R.id.rvMahasiswa)
        btnLogout = findViewById(R.id.btnLogout)
        tvUsername = findViewById(R.id.tvUsername)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        tvUsername.text = username

        rvMahasiswa.layoutManager = LinearLayoutManager(this)

        val db = DatabaseHelper(this)
        val listEvent = db.getAllEvent()
        rvMahasiswa.adapter = MahasiswaEventAdapter(listEvent, username)

        // Logout
        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Sudah di Home, tidak perlu navigasi
                    true
                }
                R.id.nav_riwayat -> {
                    val intent = Intent(this, RiwayatActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                logout()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}