package com.example.photoapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photoapp.ui.CollectionsFragment
import com.example.photoapp.ui.PhotosFragment

class PagerAdapter(
    fm: FragmentManager,
    l: Lifecycle
) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotosFragment()
            else -> CollectionsFragment()
        }
    }
}
