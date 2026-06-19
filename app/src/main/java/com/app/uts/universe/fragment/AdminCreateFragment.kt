package com.app.uts.universe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.uts.universe.R
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.viewmodel.AdminSharedViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AdminCreateFragment : Fragment() {

    private val sharedViewModel: AdminSharedViewModel by activityViewModels()

    private lateinit var etNama: TextInputEditText
    private lateinit var etKategori: TextInputEditText
    private lateinit var etTanggal: TextInputEditText
    private lateinit var etLokasi: TextInputEditText
    private lateinit var etDeskripsi: TextInputEditText
    private lateinit var etBenefit: TextInputEditText
    private lateinit var btnSimpan: MaterialButton

    private lateinit var db: DatabaseHelper
    private var selectedEventId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_admin_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DatabaseHelper(requireContext())

        etNama     = view.findViewById(R.id.etNamaEvent)
        etKategori = view.findViewById(R.id.etKategori)
        etTanggal  = view.findViewById(R.id.etTanggal)
        etLokasi   = view.findViewById(R.id.etLokasi)
        etDeskripsi = view.findViewById(R.id.etDeskripsi)
        etBenefit  = view.findViewById(R.id.etBenefit)
        btnSimpan  = view.findViewById(R.id.btnSimpan)

        // Isi form jika ada event yang mau di-edit dari Dashboard
        sharedViewModel.eventToEdit.observe(viewLifecycleOwner) { event ->
            if (event != null) {
                selectedEventId = event.id
                etNama.setText(event.nama)
                etKategori.setText(event.kategori)
                etTanggal.setText(event.tanggal)
                etLokasi.setText(event.lokasi)
                etDeskripsi.setText(event.deskripsi)
                etBenefit.setText(event.benefit)
                btnSimpan.text = "Update Event"
            }
        }

        btnSimpan.setOnClickListener {
            val nama      = etNama.text.toString().trim()
            val kategori  = etKategori.text.toString().trim()
            val tanggal   = etTanggal.text.toString().trim()
            val lokasi    = etLokasi.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()
            val benefit   = etBenefit.text.toString().trim()

            if (nama.isEmpty() || kategori.isEmpty() || tanggal.isEmpty()
                || lokasi.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val berhasil = if (selectedEventId == -1) {
                db.insertEvent(nama, kategori, tanggal, lokasi, deskripsi, benefit)
            } else {
                db.updateEvent(selectedEventId, nama, kategori, tanggal, lokasi, deskripsi, benefit)
            }

            val msg = if (berhasil) {
                if (selectedEventId == -1) "Event berhasil ditambahkan"
                else "Event berhasil diperbarui"
            } else {
                if (selectedEventId == -1) "Gagal menambahkan event"
                else "Gagal memperbarui event"
            }
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            clearForm()
        }
    }

    private fun clearForm() {
        etNama.text?.clear()
        etKategori.text?.clear()
        etTanggal.text?.clear()
        etLokasi.text?.clear()
        etDeskripsi.text?.clear()
        etBenefit.text?.clear()
        selectedEventId = -1
        btnSimpan.text = "Simpan Event"
        sharedViewModel.setEventToEdit(null)
    }
}
