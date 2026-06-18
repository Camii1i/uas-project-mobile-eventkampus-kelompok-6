package com.app.uts.universe.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import com.app.uts.universe.database.DatabaseHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailEventActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        dbHelper = DatabaseHelper(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Detail Event"

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val idEvent = intent.getIntExtra("id", 0)
        val usernameMahasiswa = intent.getStringExtra("username") ?: ""
        val namaEvent = intent.getStringExtra("nama") ?: ""
        val kategori = intent.getStringExtra("kategori") ?: ""
        val tanggal = intent.getStringExtra("tanggal") ?: ""
        val lokasi = intent.getStringExtra("lokasi") ?: ""
        val deskripsi = intent.getStringExtra("deskripsi") ?: ""
        val benefitStr = intent.getStringExtra("benefit") ?: ""

        findViewById<TextView>(R.id.tvNama).text = namaEvent
        findViewById<TextView>(R.id.tvKategori).text = kategori
        findViewById<TextView>(R.id.tvTanggal).text = tanggal
        findViewById<TextView>(R.id.tvLokasi).text = lokasi
        findViewById<TextView>(R.id.tvDeskripsi).text = deskripsi

        val tvBenefitTitle = findViewById<TextView>(R.id.tvBenefitTitle)
        val llBenefitContainer = findViewById<LinearLayout>(R.id.llBenefitContainer)

        if (benefitStr.isNotBlank()) {
            tvBenefitTitle.visibility = View.VISIBLE
            llBenefitContainer.visibility = View.VISIBLE

            val items = benefitStr.split("\n")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            for (item in items) {
                val tv = TextView(this)
                tv.text = "• $item"
                tv.setTextColor(getAttrColor(R.attr.colorTextPrimary))
                tv.textSize = 14f
                tv.setPadding(8, 8, 8, 8)
                llBenefitContainer.addView(tv)
            }
        } else {
            tvBenefitTitle.visibility = View.GONE
            llBenefitContainer.visibility = View.GONE
        }

        val btnDaftar = findViewById<MaterialButton>(R.id.btnDaftar)

        btnDaftar.setOnClickListener {
            if (usernameMahasiswa.isEmpty()) {
                Toast.makeText(
                    this,
                    "Gagal mendaftar: Sesi login tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
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
                val successIntent = Intent(this, SuccessActivity::class.java)
                successIntent.putExtra("nama_event", namaEvent)
                successIntent.putExtra("tanggal", tanggal)
                successIntent.putExtra("lokasi", lokasi)
                successIntent.putExtra("username", usernameMahasiswa)

                startActivity(successIntent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Gagal mendaftar. Silakan coba lagi.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getAttrColor(attrResId: Int): Int {
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attrResId))
        val color = typedArray.getColor(0, Color.WHITE)
        typedArray.recycle()
        return color
    }
}