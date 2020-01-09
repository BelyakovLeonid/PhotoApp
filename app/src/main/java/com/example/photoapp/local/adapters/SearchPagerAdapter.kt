package com.example.photoapp.local.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photoapp.ui.base.Router
import com.example.photoapp.ui.search.collection.SearchCollectionFragment
import com.example.photoapp.ui.search.photo.SearchPhotoFragment

class SearchPagerAdapter(
    fm: FragmentManager,
    l: Lifecycle,
    val listener: Router
) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchPhotoFragment().also { it.router = listener }
            else -> SearchCollectionFragment().also { it.router = listener }
        }
    }
}
