package com.app.uts.universe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.R
import com.app.uts.universe.adapter.MahasiswaListAdapter
import com.app.uts.universe.database.DatabaseHelper

class AdminMahasiswaFragment : Fragment() {

    private lateinit var rvMahasiswaAdmin: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_admin_mahasiswa, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMahasiswaAdmin = view.findViewById(R.id.rvMahasiswaAdmin)
        rvMahasiswaAdmin.layoutManager = LinearLayoutManager(requireContext())
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val db = DatabaseHelper(requireContext())
        rvMahasiswaAdmin.adapter = MahasiswaListAdapter(db.getAllMahasiswa())
    }
}
