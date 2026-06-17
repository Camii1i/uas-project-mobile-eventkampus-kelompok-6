package com.app.uts.universe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.R
import com.app.uts.universe.ViewModeManager
import com.app.uts.universe.model.Riwayat

class RiwayatAdapter(private val listRiwayat: ArrayList<Riwayat>) :
    RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    private var viewMode = ViewModeManager.MODE_LIST

    fun setViewMode(mode: Int) {
        if (viewMode != mode) {
            viewMode = mode
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaEvent)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggalDaftar)
    }

    override fun getItemViewType(position: Int): Int = viewMode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = when (viewType) {
            ViewModeManager.MODE_GRID -> R.layout.item_riwayat_grid
            ViewModeManager.MODE_CARD -> R.layout.item_riwayat_card
            else -> R.layout.item_riwayat
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = listRiwayat[position]
        holder.tvNama.text = riwayat.namaEvent
        holder.tvTanggal.text = riwayat.tanggalDaftar
    }

    override fun getItemCount() = listRiwayat.size
}