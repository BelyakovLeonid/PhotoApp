package com.example.photoapp.local.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photoapp.ui.Router
import com.example.photoapp.ui.fragments.collection.search.SearchCollectionFragment
import com.example.photoapp.ui.fragments.photo.search.SearchPhotoFragment

class PagerSearchAdapter(
    fm: FragmentManager,
    l: Lifecycle,
    val router: Router
) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchPhotoFragment().also { it.router = router }
            else -> SearchCollectionFragment().also { it.router = router }
        }
    }
}
