package com.app.uts.universe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.app.uts.universe.ViewModeManager
import com.app.uts.universe.model.Event
import com.app.uts.universe.R
import com.app.uts.universe.activity.DetailEventActivity

class MahasiswaEventAdapter(
    private val listEvent: ArrayList<Event>,
    private val username: String
) : RecyclerView.Adapter<MahasiswaEventAdapter.ViewHolder>() {

    private var viewMode = ViewModeManager.MODE_LIST

    fun setViewMode(mode: Int) {
        if (viewMode != mode) {
            viewMode = mode
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvLokasi: TextView = itemView.findViewById(R.id.tvLokasi)
        // btnDetail tidak ada di layout Card, jadi nullable
        val btnDetail: MaterialButton? = itemView.findViewById(R.id.btnDetail)
    }

    override fun getItemViewType(position: Int): Int = viewMode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = when (viewType) {
            ViewModeManager.MODE_GRID -> R.layout.item_event_mahasiswa_grid
            ViewModeManager.MODE_CARD -> R.layout.item_event_mahasiswa_card
            else -> R.layout.item_event_mahasiswa
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvent[position]

        holder.tvNama.text = event.nama
        holder.tvKategori.text = event.kategori
        holder.tvTanggal.text = event.tanggal
        holder.tvLokasi.text = event.lokasi

        val openDetail: (View) -> Unit = {
            val intent = Intent(holder.itemView.context, DetailEventActivity::class.java)
            intent.putExtra("nama", event.nama)
            intent.putExtra("kategori", event.kategori)
            intent.putExtra("tanggal", event.tanggal)
            intent.putExtra("lokasi", event.lokasi)
            intent.putExtra("deskripsi", event.deskripsi)
            intent.putExtra("id", event.id)
            intent.putExtra("username", username)
            holder.itemView.context.startActivity(intent)
        }

        if (holder.btnDetail != null) {
            // Mode List & Grid: tombol Detail yang diklik
            holder.btnDetail.setOnClickListener(openDetail)
        } else {
            // Mode Card: seluruh card yang diklik
            holder.itemView.setOnClickListener(openDetail)
        }
    }
}