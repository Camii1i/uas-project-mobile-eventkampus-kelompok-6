package com.app.uts.universe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.uts.universe.R

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val namaEvent = intent.getStringExtra("nama_event") ?: ""
        val tanggal = intent.getStringExtra("tanggal") ?: ""
        val lokasi = intent.getStringExtra("lokasi") ?: ""
        val username = intent.getStringExtra("username") ?: ""

        findViewById<TextView>(R.id.tvNamaEventSuccess).text = namaEvent
        findViewById<TextView>(R.id.tvTanggalSuccess).text = tanggal
        findViewById<TextView>(R.id.tvLokasiSuccess).text = lokasi

        // Tombol Lihat Riwayat
        findViewById<Button>(R.id.btnLihatRiwayat).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("openRiwayat", true) // sinyal buka tab riwayat
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // Tombol Kembali ke Home
        findViewById<Button>(R.id.btnKembaliHome).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("username", username)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}