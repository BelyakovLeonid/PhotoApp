package com.example.photoapp.ui.photo.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.data.network.response.PhotoLocation
import com.example.photoapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_details.*

class PhotoDetailFragment : BaseFragment() {
    lateinit var currentPhoto: PhotoResponse
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
        currentPhoto = commonViewModel.photoSelected!!
        bindToolbar()
        updateData()

        specialViewModel.photoDetailLiveData.observe(this, Observer {
            bindUI(it)
        })
    }

    private fun updateData() {
        specialViewModel.fetchSinglePhoto(commonViewModel.photoSelectedId!!)
    }

    private fun bindToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar_photo_details)

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_black)
                setTitle("")
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar_photo_details.inflateMenu(R.menu.details_menu)
    }

    private fun bindUI(response: PhotoDetailResponse) {
        Glide.with(view!!)
            .load(response.urls.regular)
            .placeholder(ColorDrawable(Color.parseColor(response.color)))
            .into(details_image)
        Glide.with(view!!).load(response.user.profileImage.small).into(details_profile)
        details_profile_title.text = response.user.name
        details_likes_title.text = "${response.likes} Likes"
        details_downloads_title.text = "${response.downloads} Downloads"
        details_color_title.text = response.color
        bindLocation(response.location)
        bindDate(response.createdAt)
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
}