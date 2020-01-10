package com.example.photoapp.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.ui.search.CommonSearchViewModel

abstract class BaseSearchFragment : BaseFragment() {
    lateinit var commonSearchViewModel: CommonSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonSearchViewModel =
            ViewModelProviders.of(activity!!).get(CommonSearchViewModel::class.java)
    }
}