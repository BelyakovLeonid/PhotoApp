package com.example.photoapp.ui.photo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.data.network.response.photos.response.PhotoLocation
import com.example.photoapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_details.*

class PhotoDetailFragment : BaseFragment() {
    lateinit var specialViewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialViewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)
        specialViewModel.fetchSinglePhoto(commonViewModel.photoSelectedId!!)
        specialViewModel.photoDetailLiveData.observe(this, Observer {
            bindUI(it)
        })
    }

    private fun bindUI(response: PhotoResponse) {
        Glide.with(view!!).load(response.urls.regular).into(details_image)
        Glide.with(view!!).load(response.user.profileImage.small).into(details_profile)
        details_profile_title.text = "${response.user.firstName}  ${response.user.lastName}"
        details_likes_title.text = "${response.likes} Likes"
        details_downloads_title.text = "${response.downloads} Downloads"
        details_color_title.text = response.color
        bindLocation(response.location)
        bindDate(response.createdAt)
    }

    private fun bindLocation(location: PhotoLocation?) {
        if (location != null) {
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

}