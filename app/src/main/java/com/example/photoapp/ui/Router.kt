package com.example.photoapp.ui

import android.view.View
import androidx.fragment.app.Fragment

interface Router {

    fun navigateTo(destination: Fragment, addToBackStack: Boolean = true)

    fun navigateWithSharedElement(
        sharedView: View,
        destination: Fragment,
        addToBackStack: Boolean = true
    )

    fun navigateBack()
}