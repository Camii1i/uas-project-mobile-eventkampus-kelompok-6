package com.app.uts.universe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.R
import com.app.uts.universe.model.User

/**
 * Read‑only adapter for displaying the list of mahasiswa in the admin UI.
 */
class MahasiswaListAdapter(private val listUser: List<User>) : RecyclerView.Adapter<MahasiswaListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInitial: TextView = itemView.findViewById(R.id.tvInitialMahasiswa)
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaMahasiswa)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsernameMahasiswa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvNama.text = user.nama
        holder.tvUsername.text = "@${user.username}"
        holder.tvInitial.text = if (user.nama.isNotEmpty()) user.nama[0].uppercaseChar().toString() else "M"
    }
}
