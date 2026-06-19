package com.app.uts.universe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.app.uts.universe.R
import com.app.uts.universe.util.AdminViewModeManager
import com.app.uts.universe.model.Event

/**
 * Adapter for admin event list. Supports three view types (list, grid, card) via
 * AdminViewModeManager. Each item includes Edit and Delete buttons.
 */
class AdminEventAdapter(
    private val listEvent: ArrayList<Event>,
    private val listener: OnEventClickListener
) : RecyclerView.Adapter<AdminEventAdapter.ViewHolder>() {

    interface OnEventClickListener {
        fun onEdit(event: Event)
        fun onDelete(event: Event)
    }

    private var viewMode = AdminViewModeManager.MODE_LIST

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
        val btnEdit: MaterialButton? = itemView.findViewById(R.id.btnEdit)
        val btnHapus: MaterialButton? = itemView.findViewById(R.id.btnHapus)
    }

    override fun getItemViewType(position: Int): Int = viewMode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = when (viewType) {
            AdminViewModeManager.MODE_GRID -> R.layout.item_event_admin_grid
            AdminViewModeManager.MODE_CARD -> R.layout.item_event_admin_card
            else -> R.layout.item_event_admin
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvent[position]
        holder.tvNama.text = event.nama
        holder.tvKategori.text = event.kategori
        holder.tvTanggal.text = event.tanggal
        holder.tvLokasi.text = event.lokasi
        holder.btnEdit?.setOnClickListener { listener.onEdit(event) }
        holder.btnHapus?.setOnClickListener { listener.onDelete(event) }
    }
}
