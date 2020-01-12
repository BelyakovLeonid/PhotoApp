package com.example.photoapp.ui.fragments.photo.random

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.photoapp.R
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.data.network.response.PhotoLocation
import com.example.photoapp.local.Glide.GlideApp
import com.example.photoapp.local.Glide.load
import com.example.photoapp.ui.dialogs.PhotoInfoDialogFragment
import com.example.photoapp.ui.fragments.base.BaseDetailedFragment
import com.example.photoapp.ui.fragments.photo.detail.PhotoZoomFragment
import com.example.photoapp.ui.viewmodels.photo.detail.PhotoDetailViewModel
import com.example.photoapp.ui.viewmodels.photo.detail.PhotoDetailViewModelFactory
import kotlinx.android.synthetic.main.fragment_photo_details.*
import org.kodein.di.generic.instance

class RandomPhotoFragment : BaseDetailedFragment() {
    private val viewModelFactory: PhotoDetailViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var randomPhotoViewModel: PhotoDetailViewModel
    private lateinit var currentDetailResponse: PhotoDetailResponse
    private var isFirstLaunch: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomPhotoViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PhotoDetailViewModel::class.java)

        randomPhotoViewModel.photoDetailLiveData.observe(this, Observer {
            commonViewModel.photoDetailSelected = it
            currentDetailResponse = it
            bindToolbar()
            bindUI(it)
        })

        details_image.setOnClickListener {
            goToZoom()
        }

        bindFloatingMenu()
        if (isFirstLaunch) {
            updateData()
            isFirstLaunch = false
        }
    }

    private fun bindFloatingMenu() {
        //OnClick listener not working good with motionLayout, so i use onTouchListener:
        floating_menu_download.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Toast.makeText(this.context, "downloiad", Toast.LENGTH_SHORT).show()
            }
            false
        }
        floating_menu_wallpapper.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                motion_layout.transitionToEnd()
                setWallpapers()
            }
            false
        }
        floating_menu_info.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                motion_layout.transitionToEnd()
                showInfoDialog()
            }
            false
        }
    }

    private fun setWallpapers() { //todo make progress bar

        val myWallpaperManager = WallpaperManager.getInstance(activity)

        GlideApp.with(activity!!)
            .asBitmap()
            .load(currentDetailResponse.urls.full)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    try {
                        myWallpaperManager.setBitmap(resource)
                    } catch (e: Exception) {
                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            })

    }

    private fun showInfoDialog() {
        PhotoInfoDialogFragment().apply {
            setCurrentPhoto(currentDetailResponse)
        }.show(childFragmentManager, "info_dialog")
    }

    private fun updateData() {
        randomPhotoViewModel.fetchSinglePhoto()
    }

    private fun bindUI(photo: PhotoDetailResponse) {
        bindPhoto(photo)
        GlideApp.with(view!!).load(photo.user.profileImage.small).into(details_profile)
        details_profile_title.text = photo.user.name
        details_likes.text = "${photo.likes} Likes"
        details_downloads.text = "${photo.downloads} Downloads"
        details_color_title.text = photo.color
        bindLocation(photo.location)
        bindDate(photo.createdAt)
    }

    private fun bindPhoto(photo: PhotoDetailResponse) {
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

    private fun bindLocation(location: PhotoLocation?) {
        if (location != null && !location.title.isNullOrEmpty()) {
            details_location.text = location.title
        } else {
            details_location.visibility = View.GONE
        }
    }

    private fun bindDate(date: String?) {
        if (date != null) {
            details_date.text = date
        } else {
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

    override fun getFragmentTitle() = "Random"

    override fun getUrlString() = currentDetailResponse.links.html
}