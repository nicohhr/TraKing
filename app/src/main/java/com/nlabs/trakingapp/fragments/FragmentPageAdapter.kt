package com.nlabs.trakingapp.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nlabs.trakingapp.fragments.location_list.LocationListFragment
import com.nlabs.trakingapp.fragments.route_list.RouteListFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LocationListFragment()
            1 -> RouteListFragment()
            else -> {
                LocationListFragment()
            }
        }
    }
}