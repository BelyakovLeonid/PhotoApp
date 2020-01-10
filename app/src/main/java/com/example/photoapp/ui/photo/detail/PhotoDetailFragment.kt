package com.example.photoapp.ui.photo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.data.network.response.PhotoLocation
import com.example.photoapp.local.Glide.GlideApp
import com.example.photoapp.local.Glide.load
import com.example.photoapp.ui.base.BaseDetailedFragment
import kotlinx.android.synthetic.main.fragment_photo_details.*

class PhotoDetailFragment : BaseDetailedFragment() {
    private lateinit var specialViewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialViewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)
        currentItem = commonViewModel.photoSelected!!
        bindPhoto(currentItem as PhotoResponse)
        bindToolbar()
        updateData()

        specialViewModel.photoDetailLiveData.observe(this, Observer {
            bindUI(it)
        })

        details_image.setOnClickListener {
            goToZoom()
        }
    }

    private fun updateData() {
        specialViewModel.fetchSinglePhoto(commonViewModel.photoSelectedId!!)
    }

    private fun bindPhoto(photo: PhotoResponse) {
        ViewCompat.setTransitionName(
            details_image,
            "transitionName${photo.id}"
        )

        details_image.load(
            photo.urls.regular,
            resources.displayMetrics,
            Pair(photo.width, photo.height)
        ) { startPostponedEnterTransition() }
    }

    private fun bindUI(photo: PhotoDetailResponse) {
        GlideApp.with(view!!).load(photo.user.profileImage.small).into(details_profile)
        details_profile_title.text = photo.user.name
        details_likes_title.text = "${photo.likes} Likes"
        details_downloads_title.text = "${photo.downloads} Downloads"
        details_color_title.text = photo.color
        bindLocation(photo.location)
        bindDate(photo.createdAt)
    }

    private fun bindLocation(location: PhotoLocation?) {
        if (location != null && !location.title.isNullOrEmpty()) {
            details_location_title.text = location.title
        } else {
            details_location_title.visibility = View.GONE
            details_location.visibility = View.GONE
        }
    }

    private fun bindDate(date: String?) {
        if (date != null) {
            details_date_title.text = date
        } else {
            details_date_title.visibility = View.GONE
            details_date.visibility = View.GONE
        }
    }

    private fun goToZoom() {
        val zoomFragment = PhotoZoomFragment().also {
            it.postponeEnterTransition()

            it.sharedElementEnterTransition = TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
                .setDuration(400)
        }

        router.navigateWithSharedElement(details_image, zoomFragment)
    }

    override fun getFragmentTitle() = ""
}