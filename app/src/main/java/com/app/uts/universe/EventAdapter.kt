package com.app.uts.universe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private val listEvent: ArrayList<Event>,
    private val listener: OnEventClickListener
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    interface OnEventClickListener {
        fun onEdit(event: Event)
        fun onDelete(event: Event)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvLokasi: TextView = itemView.findViewById(R.id.tvLokasi)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)

        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnHapus: Button = itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val event = listEvent[position]

        holder.tvNama.text = event.nama
        holder.tvKategori.text = "Kategori : ${event.kategori}"
        holder.tvTanggal.text = "Tanggal : ${event.tanggal}"
        holder.tvLokasi.text = "Lokasi : ${event.lokasi}"
        holder.tvDeskripsi.text = event.deskripsi

        holder.btnEdit.setOnClickListener {
            listener.onEdit(event)
        }

        holder.btnHapus.setOnClickListener {
            listener.onDelete(event)
        }
    }
}