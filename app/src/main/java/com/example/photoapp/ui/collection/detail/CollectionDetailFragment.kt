package com.example.photoapp.ui.collection.detail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.adapters.PhotoListAdapter
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router
import com.example.photoapp.ui.photo.detail.PhotoDetailFragment
import kotlinx.android.synthetic.main.fragment_collection_details.*

class CollectionDetailFragment : BaseFragment() {
    lateinit var router: Router
    private lateinit var specialViewModel: CollectionDetailViewModel
    private val adapter = PhotoListAdapter(this::goToDetails)

    var currentCollection: CollectionResponse? = null

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
        currentCollection = commonViewModel.collectionSelected
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
        router.navigateWithSharedElement(
            sharedElement,
            PhotoDetailFragment().also { it.router = router })
    }
}