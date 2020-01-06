package com.example.photoapp.ui.photo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.ui.adapters.PhotoListAdapter
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router
import com.example.photoapp.ui.photo.detail.PhotoDetailFragment
import kotlinx.android.synthetic.main.fragment_recycler.*

class PhotoListFragment : BaseFragment() {
    lateinit var listener: Router
    private lateinit var specialViewModel: PhotoListViewModel
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
        specialViewModel = ViewModelProviders.of(this).get(PhotoListViewModel::class.java)

        specialViewModel.photos.observe(this, Observer {
            adapter.submitList(it)
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.VISIBLE
        })

        specialViewModel.networkErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        updateData()

//        specialViewModel.isNetworkErrorHappened.observe(this, Observer {
//            showNetworkError(it.getValueIfNotHandled())
//        })
//
//        specialViewModel.photoListLiveData.observe(this, Observer {
//            progress_group.visibility = View.GONE
//            placeholder_group.visibility = View.GONE
//            swipe_refresh_layout.visibility = View.VISIBLE
//            recycler_view.adapter = PhotosAdapter(it, this::goToDetails)
//            recycler_view.layoutManager = LinearLayoutManager(activity)
//        })

        swipe_refresh_layout.setOnRefreshListener {
            updateData()
            swipe_refresh_layout.isRefreshing = false
        }

        placeholder_button.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        specialViewModel.fetchPhotos()
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

    private fun goToDetails(photoSelected: PhotoResponse) {
        commonViewModel.photoSelected = photoSelected
        commonViewModel.photoSelectedId = photoSelected.id
        listener.navigateTo(PhotoDetailFragment())
    }

}