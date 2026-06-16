package com.app.uts.universe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.R
import com.app.uts.universe.adapter.MahasiswaEventAdapter
import com.app.uts.universe.database.DatabaseHelper

class HomeFragment : Fragment() {

    private var username = ""

    companion object {
        fun newInstance(username: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("username", username)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.getString("username") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvMahasiswa = view.findViewById<RecyclerView>(R.id.rvMahasiswa)
        rvMahasiswa.layoutManager = LinearLayoutManager(requireContext())

        val db = DatabaseHelper(requireContext())
        val listEvent = db.getAllEvent()

        rvMahasiswa.adapter = MahasiswaEventAdapter(listEvent, username)
    }
}