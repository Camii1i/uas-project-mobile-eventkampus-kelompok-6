package com.app.uts.universe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.uts.universe.fragment.HomeFragment
import com.app.uts.universe.fragment.RiwayatFragment

class ViewPagerAdapter(
    activity: FragmentActivity,
    private val username: String
) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance(username)
            1 -> RiwayatFragment.newInstance(username)
            else -> HomeFragment.newInstance(username)
        }
    }
}