package com.example.photoapp.ui.collection.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.adapters.PhotoListAdapter
import com.example.photoapp.ui.base.BaseDetailedFragment
import com.example.photoapp.ui.photo.detail.PhotoDetailFragment
import kotlinx.android.synthetic.main.fragment_collection_details.*

class CollectionDetailFragment : BaseDetailedFragment() {
    private lateinit var specialViewModel: CollectionDetailViewModel
    private val adapter = PhotoListAdapter(this::goToDetails)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_collection_details.adapter = adapter
        specialViewModel = ViewModelProviders.of(this).get(CollectionDetailViewModel::class.java)
        currentItem = commonViewModel.collectionSelected!!
        bindToolbar()

        specialViewModel.networkErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        specialViewModel.photos.observe(this, Observer {
            adapter.submitList(it)

//            progress_group.visibility = View.GONE
//            placeholder_group.visibility = View.GONE
//            swipe_refresh_layout.visibility = View.VISIBLE
        })
//
//        swipe_refresh_layout.setOnRefreshListener {
//            updateData()
//            swipe_refresh_layout.isRefreshing = false
//        }
//
//        placeholder_button.setOnClickListener {
//            updateData()
//        }

        updateData()
    }

    private fun updateData() {
        specialViewModel.fetchPhotos(commonViewModel.collectionSelectedId!!)
    }

    private fun showNetworkError(isError: Boolean?) {
        if (isError == null) return
//
//        if (isError) {
//            progress_group.visibility = View.GONE
//            placeholder_group.visibility = View.VISIBLE
//            swipe_refresh_layout.visibility = View.GONE
//        } else {
//            placeholder_group.visibility = View.GONE
//            Toast.makeText(context, "Photos updated", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun goToDetails(sharedElement: View, photoSelected: PhotoResponse) {
        commonViewModel.photoSelected = photoSelected
        commonViewModel.photoSelectedId = photoSelected.id
        val destinationFragment = PhotoDetailFragment().also {
            it.router = router
            it.postponeEnterTransition()

            it.sharedElementEnterTransition = TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
                .setDuration(400)

            exitTransition = Fade()
        }

        router.navigateWithSharedElement(sharedElement, destinationFragment)
    }

    override fun bindToolbar() {
        super.bindToolbar()
        val collection = currentItem as CollectionResponse
        Glide.with(view!!).load(collection.user.profileImage.medium).into(collection_icon)
        collection_description.text = collection.description
        collection_name.text = "By ${collection.user.name}"
    }

    override fun getFragmentTitle() = (currentItem as CollectionResponse).title
}