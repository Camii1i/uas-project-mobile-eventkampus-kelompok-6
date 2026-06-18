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
<<<<<<< ours
=======
import com.app.uts.universe.database.DatabaseHelper
import com.google.android.material.button.MaterialButton
>>>>>>> theirs
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailEventActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
<<<<<<< ours
        // Apply saved theme so UI uses correct color attributes
=======
>>>>>>> theirs
        ThemeManager.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        // Setup toolbar as action bar with back button
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Event"

        dbHelper = DatabaseHelper(this)

        // Setup toolbar dengan back button
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Ambil data dari Intent
        val idEvent = intent.getIntExtra("id", 0)
        val usernameMahasiswa = intent.getStringExtra("username") ?: ""
        val namaEvent = intent.getStringExtra("nama") ?: ""
<<<<<<< ours
        val kategori = intent.getStringExtra("kategori")
        val tanggal = intent.getStringExtra("tanggal")
        val lokasi = intent.getStringExtra("lokasi")
        val deskripsi = intent.getStringExtra("deskripsi")
=======
        val kategori = intent.getStringExtra("kategori") ?: ""
        val tanggal = intent.getStringExtra("tanggal") ?: ""
        val lokasi = intent.getStringExtra("lokasi") ?: ""
        val deskripsi = intent.getStringExtra("deskripsi") ?: ""
>>>>>>> theirs
        val benefitStr = intent.getStringExtra("benefit") ?: ""

        // Set data ke view (tanpa label prefix, karena sudah ada di layout)
        findViewById<TextView>(R.id.tvNama).text = namaEvent
<<<<<<< ours
        // tvKategori previously showed "Kategori : ..."; keep that but remove background when setting text
        findViewById<TextView>(R.id.tvKategori).text = kategori
        findViewById<TextView>(R.id.tvTanggal).text = tanggal?.let { "Tanggal : $it" } ?: ""
        findViewById<TextView>(R.id.tvLokasi).text = lokasi?.let { "Lokasi : $it" } ?: ""
=======
        findViewById<TextView>(R.id.tvKategori).text = kategori
        findViewById<TextView>(R.id.tvTanggal).text = tanggal
        findViewById<TextView>(R.id.tvLokasi).text = lokasi
>>>>>>> theirs
        findViewById<TextView>(R.id.tvDeskripsi).text = deskripsi

        // Benefit handling (delimiter: newline)
        val tvBenefitTitle = findViewById<TextView>(R.id.tvBenefitTitle)
<<<<<<< ours
        val llBenefitContainer = findViewById<android.widget.LinearLayout>(R.id.llBenefitContainer)
        if (benefitStr.isNotBlank()) {
            tvBenefitTitle.visibility = android.view.View.VISIBLE
            llBenefitContainer.visibility = android.view.View.VISIBLE
=======
        val llBenefitContainer = findViewById<LinearLayout>(R.id.llBenefitContainer)
        if (benefitStr.isNotBlank()) {
            tvBenefitTitle.visibility = View.VISIBLE
            llBenefitContainer.visibility = View.VISIBLE
>>>>>>> theirs
            val items = benefitStr.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
            for (item in items) {
                val tv = TextView(this)
                tv.text = "• $item"
<<<<<<< ours
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
=======
                tv.setTextColor(getAttrColor(R.attr.colorTextPrimary))
                tv.textSize = 14f
                tv.setPadding(8, 8, 8, 8)
                llBenefitContainer.addView(tv)
            }
        }
>>>>>>> theirs

        // Setup tombol daftar
        val btnDaftar = findViewById<MaterialButton>(R.id.btnDaftar)
        btnDaftar.setOnClickListener {
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
                val successIntent = Intent(this, SuccessActivity::class.java)
                successIntent.putExtra("nama_event", namaEvent)
                successIntent.putExtra("tanggal", tanggal)
                successIntent.putExtra("lokasi", lokasi)
                successIntent.putExtra("username", usernameMahasiswa)
                startActivity(successIntent)
                finish()
            } else {
                Toast.makeText(this, "Gagal mendaftar. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

<<<<<<< ours
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
=======
    // Helper untuk mendapatkan color dari attr tema
    private fun getAttrColor(attrResId: Int): Int {
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attrResId))
        val color = typedArray.getColor(0, Color.WHITE)
        typedArray.recycle()
        return color
    }
}
>>>>>>> theirs
