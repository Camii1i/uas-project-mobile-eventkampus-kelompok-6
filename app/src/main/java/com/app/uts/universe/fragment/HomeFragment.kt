package com.app.uts.universe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.R
import com.app.uts.universe.ViewModeManager
import com.app.uts.universe.adapter.MahasiswaEventAdapter
import com.app.uts.universe.database.DatabaseHelper

class HomeFragment : Fragment() {

    private var username = ""

    private lateinit var rvMahasiswa: RecyclerView
    private lateinit var adapter: MahasiswaEventAdapter

    private lateinit var btnListMode: LinearLayout
    private lateinit var btnGridMode: LinearLayout
    private lateinit var btnCardMode: LinearLayout
    private lateinit var ivListMode: ImageView
    private lateinit var ivGridMode: ImageView
    private lateinit var ivCardMode: ImageView

    companion object {
        private const val GRID_COLUMNS = 2

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

        rvMahasiswa = view.findViewById(R.id.rvMahasiswa)
        btnListMode = view.findViewById(R.id.btnListMode)
        btnGridMode = view.findViewById(R.id.btnGridMode)
        btnCardMode = view.findViewById(R.id.btnCardMode)
        ivListMode = view.findViewById(R.id.ivListMode)
        ivGridMode = view.findViewById(R.id.ivGridMode)
        ivCardMode = view.findViewById(R.id.ivCardMode)

        val db = DatabaseHelper(requireContext())
        val listEvent = db.getAllEvent()

        adapter = MahasiswaEventAdapter(listEvent, username)
        rvMahasiswa.adapter = adapter

        val savedMode = ViewModeManager.getViewMode(requireContext())
        applyViewMode(savedMode)

        btnListMode.setOnClickListener { switchMode(ViewModeManager.MODE_LIST) }
        btnGridMode.setOnClickListener { switchMode(ViewModeManager.MODE_GRID) }
        btnCardMode.setOnClickListener { switchMode(ViewModeManager.MODE_CARD) }
    }

    private fun switchMode(mode: Int) {
        if (ViewModeManager.getViewMode(requireContext()) != mode) {
            ViewModeManager.setViewMode(requireContext(), mode)
            applyViewMode(mode)
        }
    }

    private fun applyViewMode(mode: Int) {
        rvMahasiswa.layoutManager = if (mode == ViewModeManager.MODE_GRID) {
            GridLayoutManager(requireContext(), GRID_COLUMNS)
        } else {
            LinearLayoutManager(requireContext())
        }

        adapter.setViewMode(mode)
        updateToggleIndicator(mode)
    }

    private fun updateToggleIndicator(mode: Int) {
        // Reset semua dulu
        btnListMode.setBackgroundResource(0)
        btnGridMode.setBackgroundResource(0)
        btnCardMode.setBackgroundResource(0)
        ivListMode.clearColorFilter()
        ivGridMode.clearColorFilter()
        ivCardMode.clearColorFilter()

        // Set yang aktif
        val activeButton = when (mode) {
            ViewModeManager.MODE_GRID -> btnGridMode to ivGridMode
            ViewModeManager.MODE_CARD -> btnCardMode to ivCardMode
            else -> btnListMode to ivListMode
        }

        activeButton.first.setBackgroundResource(R.drawable.bg_toggle_active)
        activeButton.second.setColorFilter(android.graphics.Color.WHITE)
    }
}