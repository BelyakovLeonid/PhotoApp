package com.example.photoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.photoapp.R
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router

class FlowFragment : BaseFragment(), Router {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fr = PagerFragment()
        fr.router = this
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fr)
            .commit()
    }

    override fun navigateTo(destination: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, destination)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateWithSharedElement(
        sharedView: View,
        destination: Fragment
    ) {
        childFragmentManager
            .beginTransaction()
            .setReorderingAllowed(false)
            .addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView)!!)
            .replace(R.id.fragment_container, destination)
            .addToBackStack(null)
            .commit()
    }

    fun onBackPressed() {
        childFragmentManager
            .popBackStack()

    }
}