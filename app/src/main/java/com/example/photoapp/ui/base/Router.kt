package com.example.photoapp.ui.base

import android.view.View
import androidx.fragment.app.Fragment

interface Router {

    fun navigateTo(destination: Fragment)

    fun navigateWithSharedElement(sharedView: View, destination: Fragment)

}