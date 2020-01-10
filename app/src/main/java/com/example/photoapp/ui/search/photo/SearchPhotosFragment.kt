package com.example.photoapp.ui.search.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.adapters.PhotoListAdapter
import com.example.photoapp.ui.base.BaseSearchFragment
import com.example.photoapp.ui.photo.detail.PhotoDetailFragment
import kotlinx.android.synthetic.main.fragment_recycler.*

class SearchPhotoFragment : BaseSearchFragment() {

    private val adapter = PhotoListAdapter(this::goToDetails)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view.adapter = adapter

        commonSearchViewModel.photos.observe(this, Observer {
            adapter.submitList(it)
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.VISIBLE
        })

        commonSearchViewModel.networkPhotosErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        updateData()

        swipe_refresh_layout.setOnRefreshListener {
            updateData()
            swipe_refresh_layout.isRefreshing = false
        }

        placeholder_button.setOnClickListener {
            updateData()
        }

        (activity as AppCompatActivity).supportActionBar
    }

    private fun updateData() {
//        commonSearchViewModel.fetchPhotos()
    }

    private fun showNetworkError(isError: Boolean?) {
        if (isError == null) return

        if (isError) {
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.VISIBLE
            swipe_refresh_layout.visibility = View.GONE
        } else {
            placeholder_group.visibility = View.GONE
            Toast.makeText(context, "Photos updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToDetails(sharedElement: View, photoSelected: PhotoResponse) {
        commonViewModel.photoSelected = photoSelected
        commonViewModel.photoSelectedId = photoSelected.id
        val fragment = PhotoDetailFragment().also {
            it.router = router
            it.postponeEnterTransition()

            it.sharedElementEnterTransition = TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
                .setDuration(400)

            exitTransition = Fade()
        }
        router.navigateWithSharedElement(sharedElement, fragment)
    }
}