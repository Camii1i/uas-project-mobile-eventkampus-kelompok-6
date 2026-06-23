package com.app.uts.universe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.uts.universe.fragment.AdminCreateFragment
import com.app.uts.universe.fragment.AdminDashboardFragment
import com.app.uts.universe.fragment.AdminMahasiswaFragment

class AdminViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> AdminDashboardFragment()
        1 -> AdminCreateFragment()
        2 -> AdminMahasiswaFragment()
        else -> AdminDashboardFragment()
    } as Fragment
}
