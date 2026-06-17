package com.app.uts.universe.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import com.app.uts.universe.database.DatabaseHelper
import com.google.android.material.button.MaterialButton

class ProfileAdminActivity : AppCompatActivity() {

    private lateinit var tvAdminProfileName: TextView
    private lateinit var tvAdminProfileEmail: TextView
    private lateinit var tvAdminRole: TextView
    private lateinit var tvTotalEvent: TextView
    private lateinit var tvAdminInitial: TextView
    private lateinit var btnEditAdminProfile: MaterialButton
    private lateinit var btnAdminLogout: MaterialButton
    
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        // Wajib panggil ThemeManager sebelum super.onCreate()
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_admin)

        dbHelper = DatabaseHelper(this)

        initViews()
        loadAdminData()
        loadStatistics()
        setupListeners()
    }

    private fun initViews() {
        tvAdminProfileName = findViewById(R.id.tvAdminProfileName)
        tvAdminProfileEmail = findViewById(R.id.tvAdminProfileEmail)
        tvAdminRole = findViewById(R.id.tvAdminRole)
        tvTotalEvent = findViewById(R.id.tvTotalEvent)
        tvAdminInitial = findViewById(R.id.tvAdminInitial)
        btnEditAdminProfile = findViewById(R.id.btnEditAdminProfile)
        btnAdminLogout = findViewById(R.id.btnAdminLogout)

        // Toolbar back button
        findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadAdminData() {
        val sharedPref = getSharedPreferences("admin_pref", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("admin_nama", "Administrator") ?: "Administrator"
        val email = sharedPref.getString("admin_email", "admin@universe.com") ?: "admin@universe.com"
        val role = sharedPref.getString("admin_role", "Super Admin") ?: "Super Admin"

        tvAdminProfileName.text = nama
        tvAdminProfileEmail.text = email
        tvAdminRole.text = role
        
        // Set inisial
        if (nama.isNotEmpty()) {
            tvAdminInitial.text = nama[0].uppercaseChar().toString()
        }
    }

    private fun loadStatistics() {
        // READ ONLY query SELECT COUNT dari DatabaseHelper
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM event", null)
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            tvTotalEvent.text = count.toString()
        }
        cursor.close()
    }

    private fun setupListeners() {
        btnEditAdminProfile.setOnClickListener {
            showEditDialog()
        }

        btnAdminLogout.setOnClickListener {
            logout()
        }
    }

    private fun showEditDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null)
        val etEditNama = dialogView.findViewById<EditText>(R.id.etEditNama)
        val etEditEmail = dialogView.findViewById<EditText>(R.id.etEditEmail)

        // Set current values
        etEditNama.setText(tvAdminProfileName.text)
        etEditEmail.setText(tvAdminProfileEmail.text)

        AlertDialog.Builder(this)
            .setTitle("Edit Profil Admin")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val newNama = etEditNama.text.toString().trim()
                val newEmail = etEditEmail.text.toString().trim()

                if (newNama.isNotEmpty() && newEmail.isNotEmpty()) {
                    saveAdminData(newNama, newEmail)
                    loadAdminData()
                    Toast.makeText(this, "Profil Admin diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Nama dan Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun saveAdminData(nama: String, email: String) {
        val sharedPref = getSharedPreferences("admin_pref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("admin_nama", nama)
            putString("admin_email", email)
            apply()
        }
    }

    private fun logout() {
        val sharedPref = getSharedPreferences("admin_pref", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
        
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}