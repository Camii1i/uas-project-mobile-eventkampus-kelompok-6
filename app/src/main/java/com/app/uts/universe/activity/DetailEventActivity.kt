package com.app.uts.universe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailEventActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme so UI uses correct color attributes
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        // Setup toolbar as action bar with back button
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Event"

        dbHelper = DatabaseHelper(this)

        // 1. Menangkap ID Event dan Username dari Adapter (Tanpa data dummy "mahasiswa_test" lagi)
        val idEvent = intent.getIntExtra("id", 0)
        val usernameMahasiswa = intent.getStringExtra("username") ?: ""

        val namaEvent = intent.getStringExtra("nama") ?: ""
        val kategori = intent.getStringExtra("kategori")
        val tanggal = intent.getStringExtra("tanggal")
        val lokasi = intent.getStringExtra("lokasi")
        val deskripsi = intent.getStringExtra("deskripsi")
        val benefitStr = intent.getStringExtra("benefit") ?: ""

        findViewById<TextView>(R.id.tvNama).text = namaEvent
        // tvKategori previously showed "Kategori : ..."; keep that but remove background when setting text
        findViewById<TextView>(R.id.tvKategori).text = kategori
        findViewById<TextView>(R.id.tvTanggal).text = tanggal?.let { "Tanggal : $it" } ?: ""
        findViewById<TextView>(R.id.tvLokasi).text = lokasi?.let { "Lokasi : $it" } ?: ""
        findViewById<TextView>(R.id.tvDeskripsi).text = deskripsi

        // Benefit handling (delimiter: newline)
        val tvBenefitTitle = findViewById<TextView>(R.id.tvBenefitTitle)
        val llBenefitContainer = findViewById<android.widget.LinearLayout>(R.id.llBenefitContainer)
        if (benefitStr.isNotBlank()) {
            tvBenefitTitle.visibility = android.view.View.VISIBLE
            llBenefitContainer.visibility = android.view.View.VISIBLE
            val items = benefitStr.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
            for (item in items) {
                val tv = TextView(this)
                tv.text = "• $item"
                tv.setTextColor(android.graphics.Color.parseColor("#F1F5F9"))
                tv.textSize = 14f
                tv.setPadding(8,8,8,8)
                llBenefitContainer.addView(tv)
            }
        } else {
            tvBenefitTitle.visibility = android.view.View.GONE
            llBenefitContainer.visibility = android.view.View.GONE
        }

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}