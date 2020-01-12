package com.example.photoapp.ui.fragments.collection.detail

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
import com.example.photoapp.ui.fragments.base.BaseDetailedFragment
import com.example.photoapp.ui.fragments.photo.detail.PhotoDetailFragment
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModel
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModelFactory
import kotlinx.android.synthetic.main.fragment_collection_details.*
import org.kodein.di.generic.instance

class CollectionDetailFragment : BaseDetailedFragment() {
    private val viewModelFactory: PhotoListViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var specialViewModel: PhotoListViewModel
    private lateinit var currentCollectionResponse: CollectionResponse
    private val adapter = PhotoListAdapter(this::goToDetails)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_collection_details.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        specialViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel::class.java)
        currentCollectionResponse = commonViewModel.collectionSelected!!
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
        specialViewModel.fetchPhotos(commonViewModel.collectionSelectedId.toString())
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
        Glide.with(view!!).load(currentCollectionResponse.user.profileImage.medium)
            .into(collection_icon)
        collection_description.text = currentCollectionResponse.description
        collection_name.text = "By ${currentCollectionResponse.user.name}"
    }

    override fun getFragmentTitle() = currentCollectionResponse.title
    override fun getUrlString() = currentCollectionResponse.links.html
}