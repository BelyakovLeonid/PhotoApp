package com.example.photoapp.ui.photo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoapp.R
import com.example.photoapp.ui.adapters.PhotosAdapter
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router
import com.example.photoapp.ui.photo.detail.PhotoDetailFragment
import kotlinx.android.synthetic.main.fragment_recycler.*

class PhotoListFragment : BaseFragment() {
    lateinit var specialViewModel: PhotoListViewModel
    lateinit var listener: Router

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        specialViewModel = ViewModelProviders.of(this).get(PhotoListViewModel::class.java)
        updateData()

        specialViewModel.isNetworkErrorHappened.observe(this, Observer {
            showNetworkError(it.getValueIfNotHandled())
        })

        specialViewModel.photoListLiveData.observe(this, Observer {
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.VISIBLE
            recycler_view.adapter = PhotosAdapter(it, this::goToDetails)
            recycler_view.layoutManager = LinearLayoutManager(activity)
        })

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

    private fun goToDetails(photoSelectedId: String) {
        commonViewModel.photoSelectedId = photoSelectedId
        listener.navigateTo(PhotoDetailFragment())
    }

}