package com.example.photoapp.ui.fragments.photo.detail

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
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.data.network.response.PhotoLocation
import com.example.photoapp.local.Glide.GlideApp
import com.example.photoapp.local.Glide.load
import com.example.photoapp.ui.dialogs.PhotoInfoDialogFragment
import com.example.photoapp.ui.fragments.base.BaseDetailedFragment
import com.example.photoapp.ui.viewmodels.photo.detail.PhotoDetailViewModel
import com.example.photoapp.ui.viewmodels.photo.detail.PhotoDetailViewModelFactory
import kotlinx.android.synthetic.main.fragment_photo_details.*
import org.kodein.di.generic.instance


class PhotoDetailFragment : BaseDetailedFragment() {

    private val viewModelFactory: PhotoDetailViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var specialViewModel: PhotoDetailViewModel
    private lateinit var currentPhotoDetailResponse: PhotoDetailResponse
    private lateinit var currentPhotoResponse: PhotoResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PhotoDetailViewModel::class.java)
        currentPhotoResponse = commonViewModel.photoSelected!!
        bindPhoto(currentPhotoResponse)
        bindToolbar()
        updateData()

        specialViewModel.photoDetailLiveData.observe(this, Observer {
            commonViewModel.photoDetailSelected = it
            currentPhotoDetailResponse = it
            bindUI(it)
        })

        details_image.setOnClickListener {
            goToZoom()
        }

        bindFloatingMenu()
    }

    private fun bindFloatingMenu() {
        //OnClick listener not working good with motionLayout, so i use onTouchListener:
        floating_menu_download.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Toast.makeText(this.context, "Not implemented yet", Toast.LENGTH_SHORT).show()
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

        val myWallpaperManager = WallpaperManager.getInstance(this@PhotoDetailFragment.context)

        GlideApp.with(this@PhotoDetailFragment.activity!!)
            .asBitmap()
            .load(currentPhotoDetailResponse.urls.full)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.toast_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    try {
                        myWallpaperManager.setBitmap(resource)
                    } catch (e: Exception) {
                        Toast.makeText(
                            activity,
                            resources.getString(R.string.toast_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun showInfoDialog() {
        PhotoInfoDialogFragment().apply {
            setCurrentPhoto(currentPhotoDetailResponse)
        }.show(childFragmentManager, "info_dialog")
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
        details_likes.text = resources.getString(R.string.photos_detail_likes, photo.likes)
        details_downloads.text =
            resources.getString(R.string.photos_detail_downloads, photo.downloads)
        details_color_title.text = photo.color
        bindLocation(photo.location)
        bindDate(photo.createdAt)
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
        val destinationFragment = PhotoZoomFragment().also {
            it.postponeEnterTransition()

            it.sharedElementEnterTransition = TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
                .setDuration(400)
        }

        router.navigateWithSharedElement(details_image, destinationFragment)
    }

    override fun getFragmentTitle() = ""
    override fun getUrlString() = currentPhotoResponse.links.html

}