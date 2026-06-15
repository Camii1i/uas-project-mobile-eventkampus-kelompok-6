package com.app.uts.universe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.adapter.MahasiswaEventAdapter
import com.app.uts.universe.R

class HomeActivity : AppCompatActivity() {

    private lateinit var rvMahasiswa: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1. Tangkap username dari halaman Login
        val username = intent.getStringExtra("username") ?: ""

        // 2. Inisialisasi RecyclerView
        rvMahasiswa = findViewById(R.id.rvMahasiswa)
        rvMahasiswa.layoutManager = LinearLayoutManager(this)

        // 3. Ambil data event dari database
        val db = DatabaseHelper(this)
        val listEvent = db.getAllEvent()

        // 4. Masukkan listEvent DAN username ke dalam Adapter
        val adapter = MahasiswaEventAdapter(listEvent, username)
        rvMahasiswa.adapter = adapter

        val btnRiwayat = findViewById<Button>(R.id.btnLihatRiwayat) // atau R.id.fabRiwayat
        btnRiwayat.setOnClickListener {
            val intent = Intent(this, RiwayatActivity::class.java)
            intent.putExtra("username", username) // PENTING: Oper username-nya!
            startActivity(intent)
        }
    }
}