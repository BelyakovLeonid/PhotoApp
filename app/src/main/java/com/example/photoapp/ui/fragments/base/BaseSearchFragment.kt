package com.example.photoapp.ui.fragments.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.ui.viewmodels.search.CommonSearchViewModel

abstract class BaseSearchFragment : BaseFragment() {

    protected lateinit var commonSearchViewModel: CommonSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        commonSearchViewModel = if (parentFragment != null)
            ViewModelProviders.of(parentFragment!!).get(CommonSearchViewModel::class.java)
        else
            ViewModelProviders.of(this).get(CommonSearchViewModel::class.java)
    }
}