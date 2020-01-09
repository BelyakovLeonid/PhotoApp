package com.example.photoapp.ui.photo.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router
import kotlinx.android.synthetic.main.fragment_photo_details.*

class PhotoDetailFragment : BaseFragment() {
    lateinit var router: Router
    private lateinit var currentPhoto: PhotoResponse
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
        currentPhoto = commonViewModel.photoSelected!!
        ViewCompat.setTransitionName(details_image, "transitionName${currentPhoto.id}")
        bindToolbar()
        bindPhoto()
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

    private fun bindPhoto() {
        details_image.load(
            currentPhoto.urls.regular,
            resources.displayMetrics,
            Pair(currentPhoto.width, currentPhoto.height)
        ) { startPostponedEnterTransition() }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                router.navigateBack()
            }
            R.id.toolbar_browser -> {
                openBrowser()
            }
            R.id.toolbar_share -> {
                sharePhoto()
            }
        }
        return true
    }

    private fun openBrowser() { //todo проверку
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(currentPhoto.links.html) }
        startActivity(intent)
    }

    private fun sharePhoto() {  //todo проверку
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, currentPhoto.links.html)
            type = "text/html"
        }
        startActivity(intent)
    }

    private fun bindUI(response: PhotoDetailResponse) {
        GlideApp.with(view!!).load(response.user.profileImage.small).into(details_profile)
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
}