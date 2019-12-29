package com.example.photoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoapp.R
import com.example.photoapp.ui.base.ScopedFragment

class MainFlowFragment : ScopedFragment(), Router {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fr = PagerFragment()
        fr.listener = this
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fr)
            .commit()
    }

    override fun navigateTo() {
        val fr = PhotoDetailsFragment()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fr)
            .addToBackStack(null)
            .commit()
    }

    fun onBackPressed() {
        childFragmentManager.popBackStackImmediate()
    }
}