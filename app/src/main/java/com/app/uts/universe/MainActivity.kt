package com.app.uts.universe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val db = DatabaseHelper(this)
        db.writableDatabase

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        // Pindah ke halaman register
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Login
        btnLogin.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Username dan Password harus diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val role = db.checkLogin(username, password)

            when (role) {

                "admin" -> {

                    Toast.makeText(
                        this,
                        "Login Admin Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(this, AdminActivity::class.java)
                    )

                    finish()
                }

                "mahasiswa" -> {

                    Toast.makeText(
                        this,
                        "Login Mahasiswa Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Kirim username ke HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)

                    intent.putExtra(
                        "username",
                        username
                    )

                    startActivity(intent)

                    finish()
                }

                else -> {

                    Toast.makeText(
                        this,
                        "Username atau Password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }
}