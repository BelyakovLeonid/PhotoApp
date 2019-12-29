package com.example.photoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoapp.R
import com.example.photoapp.ui.adapters.CollectionsAdapter
import com.example.photoapp.ui.base.ScopedFragment
import com.example.photoapp.ui.viewmodels.PhotosViewModel
import kotlinx.android.synthetic.main.fragment_recycler.*

class CollectionsFragment : ScopedFragment() {
    lateinit var viewModel: PhotosViewModel
    lateinit var listener: Router

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PhotosViewModel::class.java)
        updateData()

        viewModel.isNetworkErrorHappened.observe(this, Observer {
            showNetworkError(it.getValueIfNotHandled())
        })

        viewModel.collectionsLiveData.observe(this, Observer {
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.VISIBLE
            recycler_view.adapter = CollectionsAdapter(it, this::goToDetails)
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

    fun updateData() {
        viewModel.fetchCollections()
    }

    private fun showNetworkError(isError: Boolean?) {
        if (isError == null) return

        if (isError) {
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.VISIBLE
            swipe_refresh_layout.visibility = View.GONE
        } else {
            placeholder_group.visibility = View.GONE
            Toast.makeText(context, "Collections updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToDetails() {
        listener.navigateTo()
    }
}
