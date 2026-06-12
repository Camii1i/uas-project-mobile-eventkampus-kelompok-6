package com.app.uts.universe

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MahasiswaEventAdapter(
    private val listEvent: ArrayList<Event>,
    private val username: String
) : RecyclerView.Adapter<MahasiswaEventAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvLokasi: TextView = itemView.findViewById(R.id.tvLokasi)
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_mahasiswa, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val event = listEvent[position]

        holder.tvNama.text = event.nama
        holder.tvKategori.text = "Kategori : ${event.kategori}"
        holder.tvTanggal.text = "Tanggal : ${event.tanggal}"
        holder.tvLokasi.text = "Lokasi : ${event.lokasi}"

        holder.btnDetail.setOnClickListener {

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
    }
}