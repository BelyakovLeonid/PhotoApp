package com.example.photoapp.ui.fragments.photo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoapp.R
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.local.Glide.load
import com.example.photoapp.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_zoom.*

class PhotoZoomFragment : BaseFragment() {
    private lateinit var currentPhoto: PhotoDetailResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPhoto = commonViewModel.photoDetailSelected!!
        photo_view.transitionName = "transitionName${currentPhoto.id}"
        bindUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
    }

    private fun bindUI() {
        photo_view.load(
            currentPhoto.urls.regular,
            resources.displayMetrics,
            Pair(currentPhoto.width, currentPhoto.height)
        ) { startPostponedEnterTransition() }
    }
}