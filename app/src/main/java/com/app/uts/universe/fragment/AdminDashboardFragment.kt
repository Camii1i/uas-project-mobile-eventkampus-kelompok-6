package com.app.uts.universe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.uts.universe.R
import com.app.uts.universe.adapter.AdminEventAdapter
import com.app.uts.universe.database.DatabaseHelper
import com.app.uts.universe.model.Event
import com.app.uts.universe.util.AdminViewModeManager
import com.app.uts.universe.viewmodel.AdminSharedViewModel
import com.app.uts.universe.activity.AdminActivity

class AdminDashboardFragment :
    Fragment(),
    AdminEventAdapter.OnEventClickListener {

    private val sharedViewModel: AdminSharedViewModel by activityViewModels()

    private lateinit var rvEventAdmin: RecyclerView
    private lateinit var adapter: AdminEventAdapter
    private lateinit var db: DatabaseHelper

    private lateinit var btnListModeAdmin: LinearLayout
    private lateinit var btnGridModeAdmin: LinearLayout
    private lateinit var btnCardModeAdmin: LinearLayout
    private lateinit var ivListModeAdmin: ImageView
    private lateinit var ivGridModeAdmin: ImageView
    private lateinit var ivCardModeAdmin: ImageView

    companion object {
        private const val GRID_COLUMNS = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DatabaseHelper(requireContext())
        rvEventAdmin = view.findViewById(R.id.rvEventAdmin)
        btnListModeAdmin = view.findViewById(R.id.btnListModeAdmin)
        btnGridModeAdmin = view.findViewById(R.id.btnGridModeAdmin)
        btnCardModeAdmin = view.findViewById(R.id.btnCardModeAdmin)
        ivListModeAdmin = view.findViewById(R.id.ivListModeAdmin)
        ivGridModeAdmin = view.findViewById(R.id.ivGridModeAdmin)
        ivCardModeAdmin = view.findViewById(R.id.ivCardModeAdmin)

        loadData()
        applyViewMode(AdminViewModeManager.getViewMode(requireContext()))

        btnListModeAdmin.setOnClickListener { switchMode(AdminViewModeManager.MODE_LIST) }
        btnGridModeAdmin.setOnClickListener { switchMode(AdminViewModeManager.MODE_GRID) }
        btnCardModeAdmin.setOnClickListener { switchMode(AdminViewModeManager.MODE_CARD) }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        adapter = AdminEventAdapter(db.getAllEvent(), this)
        adapter.setViewMode(AdminViewModeManager.getViewMode(requireContext()))
        rvEventAdmin.adapter = adapter
    }

    private fun switchMode(mode: Int) {
        if (AdminViewModeManager.getViewMode(requireContext()) != mode) {
            AdminViewModeManager.setViewMode(requireContext(), mode)
            applyViewMode(mode)
        }
    }

    private fun applyViewMode(mode: Int) {
        rvEventAdmin.layoutManager = if (mode == AdminViewModeManager.MODE_GRID) {
            GridLayoutManager(requireContext(), GRID_COLUMNS)
        } else {
            LinearLayoutManager(requireContext())
        }
        adapter.setViewMode(mode)
        updateToggleIndicator(mode)
    }

    private fun updateToggleIndicator(mode: Int) {
        btnListModeAdmin.setBackgroundResource(0)
        btnGridModeAdmin.setBackgroundResource(0)
        btnCardModeAdmin.setBackgroundResource(0)
        ivListModeAdmin.clearColorFilter()
        ivGridModeAdmin.clearColorFilter()
        ivCardModeAdmin.clearColorFilter()

        val (activeBtn, activeIv) = when (mode) {
            AdminViewModeManager.MODE_GRID -> btnGridModeAdmin to ivGridModeAdmin
            AdminViewModeManager.MODE_CARD -> btnCardModeAdmin to ivCardModeAdmin
            else -> btnListModeAdmin to ivListModeAdmin
        }
        activeBtn.setBackgroundResource(R.drawable.bg_toggle_active)
        activeIv.setColorFilter(android.graphics.Color.WHITE)
    }

    override fun onEdit(event: Event) {
        sharedViewModel.setEventToEdit(event)
        (activity as? AdminActivity)?.goToCreateTab()
    }

    override fun onDelete(event: Event) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Event")
            .setMessage("Yakin ingin menghapus event ini?")
            .setPositiveButton("Ya") { _, _ ->
                if (db.deleteEvent(event.id)) {
                    Toast.makeText(requireContext(), "Event dihapus", Toast.LENGTH_SHORT).show()
                    loadData()
                } else {
                    Toast.makeText(requireContext(), "Gagal menghapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}
