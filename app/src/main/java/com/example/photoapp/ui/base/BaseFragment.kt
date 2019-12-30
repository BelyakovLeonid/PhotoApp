package com.example.photoapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.ui.CommonViewModel

abstract class BaseFragment : Fragment() {

    lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonViewModel = ViewModelProviders.of(activity!!).get(CommonViewModel::class.java)
    }
}