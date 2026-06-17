package com.app.uts.universe.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.model.Event
import com.app.uts.universe.adapter.EventAdapter
import com.app.uts.universe.R
import com.app.uts.universe.ThemeManager
import com.google.android.material.card.MaterialCardView

class AdminActivity :
    AppCompatActivity(),
    EventAdapter.OnEventClickListener {

    private lateinit var rvEvent: RecyclerView

    private lateinit var etNama: EditText
    private lateinit var etKategori: EditText
    private lateinit var etTanggal: EditText
    private lateinit var etLokasi: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnLogout: Button
    private lateinit var btnProfileAdmin: MaterialCardView

    private lateinit var db: DatabaseHelper

    private var selectedEventId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySavedTheme(this) // Wajib panggil ThemeManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        db = DatabaseHelper(this)

        rvEvent = findViewById(R.id.rvEvent)
        rvEvent.layoutManager = LinearLayoutManager(this)

        etNama = findViewById(R.id.etNamaEvent)
        etKategori = findViewById(R.id.etKategori)
        etTanggal = findViewById(R.id.etTanggal)
        etLokasi = findViewById(R.id.etLokasi)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnLogout = findViewById(R.id.btnLogout)
        btnProfileAdmin = findViewById(R.id.btnProfileAdmin)

        loadData()

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        // Ke Halaman Profil Admin
        btnProfileAdmin.setOnClickListener {
            startActivity(Intent(this, ProfileAdminActivity::class.java))
        }

        btnSimpan.setOnClickListener {

            val nama = etNama.text.toString().trim()
            val kategori = etKategori.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()
            val lokasi = etLokasi.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()

            if (nama.isEmpty() ||
                kategori.isEmpty() ||
                tanggal.isEmpty() ||
                lokasi.isEmpty() ||
                deskripsi.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Semua field harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (selectedEventId == -1) {
                val berhasil = db.insertEvent(nama, kategori, tanggal, lokasi, deskripsi)
                if (berhasil) {
                    Toast.makeText(this, "Event berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    clearForm()
                    loadData()
                } else {
                    Toast.makeText(this, "Gagal menambahkan event", Toast.LENGTH_SHORT).show()
                }
            } else {
                val berhasil = db.updateEvent(selectedEventId, nama, kategori, tanggal, lokasi, deskripsi)
                if (berhasil) {
                    Toast.makeText(this, "Event berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    clearForm()
                    loadData()
                } else {
                    Toast.makeText(this, "Gagal memperbarui event", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                logout()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun loadData() {
        val listEvent = db.getAllEvent()
        rvEvent.adapter = EventAdapter(listEvent, this)
    }

    private fun clearForm() {
        etNama.text.clear()
        etKategori.text.clear()
        etTanggal.text.clear()
        etLokasi.text.clear()
        etDeskripsi.text.clear()
        selectedEventId = -1
        btnSimpan.text = "Simpan Event"
    }

    override fun onEdit(event: Event) {
        selectedEventId = event.id
        etNama.setText(event.nama)
        etKategori.setText(event.kategori)
        etTanggal.setText(event.tanggal)
        etLokasi.setText(event.lokasi)
        etDeskripsi.setText(event.deskripsi)
        btnSimpan.text = "Update Event"
    }

    override fun onDelete(event: Event) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Event")
            .setMessage("Yakin ingin menghapus event ini?")
            .setPositiveButton("Ya") { _, _ ->
                val berhasil = db.deleteEvent(event.id)
                if (berhasil) {
                    Toast.makeText(this, "Event berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadData()
                } else {
                    Toast.makeText(this, "Gagal menghapus event", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}