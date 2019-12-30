package com.example.photoapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photoapp.ui.base.Router
import com.example.photoapp.ui.collection.list.CollectionListFragment
import com.example.photoapp.ui.photo.list.PhotoListFragment

class PagerAdapter(
    fm: FragmentManager,
    l: Lifecycle,
    val listener: Router
) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoListFragment().also { it.listener = listener }
            else -> CollectionListFragment().also { it.listener = listener }
        }
    }
}
