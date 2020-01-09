package com.example.photoapp.ui.photo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.Glide.load
import com.example.photoapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_zoom.*

class PhotoZoomFragment : BaseFragment() {
    private lateinit var currentPhoto: PhotoResponse
    private lateinit var specialViewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialViewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)
        currentPhoto = commonViewModel.photoSelected!!
        photo_view.transitionName = "transitionName${currentPhoto.id}"
        updateData()

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

    private fun updateData() {
        specialViewModel.fetchSinglePhoto(commonViewModel.photoSelectedId!!)
    }
}