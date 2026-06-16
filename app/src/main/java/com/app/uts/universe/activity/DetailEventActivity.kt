package com.app.uts.universe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailEventActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        dbHelper = DatabaseHelper(this)

        // 1. Menangkap ID Event dan Username dari Adapter (Tanpa data dummy "mahasiswa_test" lagi)
        val idEvent = intent.getIntExtra("id", 0)
        val usernameMahasiswa = intent.getStringExtra("username") ?: ""

        val namaEvent = intent.getStringExtra("nama") ?: ""
        val kategori = intent.getStringExtra("kategori")
        val tanggal = intent.getStringExtra("tanggal")
        val lokasi = intent.getStringExtra("lokasi")
        val deskripsi = intent.getStringExtra("deskripsi")

        findViewById<TextView>(R.id.tvNama).text = namaEvent
        findViewById<TextView>(R.id.tvKategori).text = "Kategori : $kategori"
        findViewById<TextView>(R.id.tvTanggal).text = "Tanggal : $tanggal"
        findViewById<TextView>(R.id.tvLokasi).text = "Lokasi : $lokasi"
        findViewById<TextView>(R.id.tvDeskripsi).text = deskripsi

        val btnDaftar = findViewById<Button>(R.id.btnDaftar)

        btnDaftar.setOnClickListener {
            // 2. Tambahan validasi: Cegah daftar jika username gagal ditangkap
            if (usernameMahasiswa.isEmpty()) {
                Toast.makeText(this, "Gagal mendaftar: Sesi login tidak ditemukan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val tanggalDaftarSekarang = sdf.format(Date())

            val isSuccess = dbHelper.insertRiwayat(
                usernameMahasiswa = usernameMahasiswa,
                idEvent = idEvent,
                namaEvent = namaEvent,
                tanggalDaftar = tanggalDaftarSekarang
            )

            if (isSuccess) {
                val intent = Intent(this, SuccessActivity::class.java)
                intent.putExtra("nama_event", namaEvent)
                intent.putExtra("tanggal", tanggal)
                intent.putExtra("lokasi", lokasi)
                intent.putExtra("username", usernameMahasiswa)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Gagal mendaftar. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}