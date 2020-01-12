package com.example.photoapp.local.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photoapp.ui.Router
import com.example.photoapp.ui.fragments.collection.list.CollectionListFragment
import com.example.photoapp.ui.fragments.photo.list.PhotoListFragment

class PagerAdapter(
    fm: FragmentManager,
    l: Lifecycle,
    val router: Router
) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoListFragment().also { it.router = router }
            else -> CollectionListFragment().also { it.router = router }
        }
    }
}
