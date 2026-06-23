package com.app.uts.universe.activity

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnBackToLogin = findViewById<MaterialButton>(R.id.btnBackToLogin)

        btnBackToLogin.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {

            val nama = etNama.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else {

                val db = dbHelper.writableDatabase
                val values = ContentValues()

                values.put("nama", nama)
                values.put("username", username)
                values.put("password", password)
                values.put("role", "mahasiswa")

                db.insert("user", null, values)

                Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }
}