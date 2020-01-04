package com.example.photoapp.ui.collection.detail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.data.network.response.photos.response.ListResponse
import com.example.photoapp.ui.adapters.PhotosAdapter
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router
import com.example.photoapp.ui.photo.detail.PhotoDetailFragment
import kotlinx.android.synthetic.main.fragment_collection_details.*

class CollectionDetailFragment : BaseFragment() {
    lateinit var specialViewModel: CollectionDetailViewModel
    lateinit var listener: Router
    var currentCollection: CollectionsListResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        specialViewModel = ViewModelProviders.of(this).get(CollectionDetailViewModel::class.java)
        currentCollection = commonViewModel.collectionSelected
        bindToolbar()
        updateData()

        specialViewModel.isNetworkErrorHappened.observe(this, Observer {
            showNetworkError(it.getValueIfNotHandled())
        })

        specialViewModel.collectionPhotosLiveData.observe(this, Observer {
            //            progress_group.visibility = View.GONE
//            placeholder_group.visibility = View.GONE
//            swipe_refresh_layout.visibility = View.VISIBLE
            recycler_collection_details.adapter = PhotosAdapter(it, this::goToDetails)
            recycler_collection_details.layoutManager = LinearLayoutManager(activity)
        })

//        swipe_refresh_layout.setOnRefreshListener {
//            updateData()
//            swipe_refresh_layout.isRefreshing = false
//        }
//
//        placeholder_button.setOnClickListener {
//            updateData()
//        }
    }

    private fun bindToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar_collection_details)

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_black)
                setTitle(currentCollection?.title)
            }
        }

        setHasOptionsMenu(true)
        Glide.with(view!!).load(currentCollection?.user?.profileImage?.medium).into(collection_icon)
        collection_description.text = currentCollection?.description
        collection_name.text = "By ${currentCollection?.user?.name}"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar_collection_details.inflateMenu(R.menu.details_menu)
    }

    private fun updateData() {
        specialViewModel.fetchCollectionPhotos(commonViewModel.collectionSelectedId!!)
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

    private fun goToDetails(photoSelected: ListResponse) {
        commonViewModel.photoSelected = photoSelected
        commonViewModel.photoSelectedId = photoSelected.id
        listener.navigateTo(PhotoDetailFragment())
    }
}