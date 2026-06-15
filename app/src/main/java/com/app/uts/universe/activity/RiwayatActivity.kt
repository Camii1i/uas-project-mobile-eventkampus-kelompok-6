package com.app.uts.universe.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.R
import com.app.uts.universe.adapter.RiwayatAdapter

class RiwayatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        val username = intent.getStringExtra("username") ?: ""
        val db = DatabaseHelper(this)

        val listRiwayat = db.getRiwayatMahasiswa(username)

        val rvRiwayat = findViewById<RecyclerView>(R.id.rvRiwayat)
        rvRiwayat.layoutManager = LinearLayoutManager(this)

        // Kamu perlu buat Adapter untuk Riwayat juga
        rvRiwayat.adapter = RiwayatAdapter(listRiwayat)
    }
}