package com.example.photoapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.data.repository.PhotoDataSource
import kotlinx.android.synthetic.main.fragment_photo_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoDetailsFragment : ScopedFragment() {
    val source = PhotoDataSource()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateData()
    }

    private fun updateData() {
        GlobalScope.launch {
            source.fetchPhoto()
        }

        source.downloadedPhoto.observe(this, Observer {
            bindUI(it)
        })

    }

    private fun bindUI(response: PhotoResponse) {
        Glide.with(view!!).load(response.urls.regular).into(details_image)
        Glide.with(view!!).load(response.user.profileImage.small).into(details_profile)
        details_profile_title.text = "${response.user.firstName}  ${response.user.lastName}"
        details_location_title.text = response.location.title
        details_date_title.text = response.createdAt
        details_likes_title.text = "${response.likes} Likes"
        details_downloads_title.text = "${response.downloads} Downloads"
        details_color_title.text = response.color
    }
}