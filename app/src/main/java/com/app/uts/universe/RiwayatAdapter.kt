package com.app.uts.universe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RiwayatAdapter(private val listRiwayat: ArrayList<Riwayat>) :
    RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaEvent)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggalDaftar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = listRiwayat[position]
        holder.tvNama.text = riwayat.namaEvent
        holder.tvTanggal.text = "Daftar pada: ${riwayat.tanggalDaftar}"
    }

    override fun getItemCount() = listRiwayat.size
}