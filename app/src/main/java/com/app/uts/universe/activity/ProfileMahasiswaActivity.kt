package com.app.uts.universe.activity

import android.content.ContentValues
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

class ProfileMahasiswaActivity : AppCompatActivity() {

    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileNim: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var tvAvatarInitial: TextView
    private lateinit var btnEditProfile: MaterialButton
    private lateinit var btnLogout: MaterialButton

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_mahasiswa)

        dbHelper = DatabaseHelper(this)

        initViews()
        loadUserData()
        setupListeners()
    }

    private fun initViews() {
        tvProfileName = findViewById(R.id.tvProfileName)
        tvProfileNim = findViewById(R.id.tvProfileNim)
        tvProfileEmail = findViewById(R.id.tvProfileEmail)
        tvAvatarInitial = findViewById(R.id.tvAvatarInitial)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnLogout = findViewById(R.id.btnLogout)

        findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadUserData() {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "") ?: ""

        var nama = ""
        
        if (username.isNotEmpty()) {
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT nama FROM user WHERE username = ?", arrayOf(username))
            if (cursor.moveToFirst()) {
                nama = cursor.getString(0) ?: ""
            }
            cursor.close()
        }

        if (nama.isEmpty()) {
            nama = sharedPref.getString("nama", username.ifEmpty { "Mahasiswa" }) ?: "Mahasiswa"
        }

        val nim = sharedPref.getString("nim", "2410501123") ?: "2410501123"
        val email = sharedPref.getString("email", if (username.isNotEmpty()) "$username@upnvj.ac.id" else "mahasiswa@upnvj.ac.id") ?: ""

        tvProfileName.text = nama
        tvProfileNim.text = nim
        tvProfileEmail.text = email
        
        if (nama.isNotEmpty()) {
            tvAvatarInitial.text = nama[0].uppercaseChar().toString()
        }
    }

    private fun setupListeners() {
        btnEditProfile.setOnClickListener {
            showEditDialog()
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun showEditDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null)
        val etEditNama = dialogView.findViewById<EditText>(R.id.etEditNama)
        val etEditEmail = dialogView.findViewById<EditText>(R.id.etEditEmail)

        etEditNama.setText(tvProfileName.text)
        etEditEmail.setText(tvProfileEmail.text)

        AlertDialog.Builder(this)
            .setTitle("Edit Profil")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val newNama = etEditNama.text.toString().trim()
                val newEmail = etEditEmail.text.toString().trim()

                if (newNama.isNotEmpty() && newEmail.isNotEmpty()) {
                    saveUserData(newNama, newEmail)
                    loadUserData()
                    Toast.makeText(this, "Profil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Nama dan Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun saveUserData(nama: String, email: String) {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "") ?: ""

        if (username.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("nama", nama)
            db.update("user", values, "username = ?", arrayOf(username))
        }

        with(sharedPref.edit()) {
            putString("nama", nama)
            putString("email", email)
            apply()
        }
    }

    private fun logout() {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
        
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}