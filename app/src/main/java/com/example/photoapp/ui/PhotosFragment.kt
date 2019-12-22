package com.example.photoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoapp.R
import com.example.photoapp.data.repository.PhotoDataSource
import com.example.photoapp.ui.adapters.PhotosAdapter
import com.example.photoapp.ui.base.PhotoDetailsFragment
import com.example.photoapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotosFragment : ScopedFragment() {
    val source = PhotoDataSource()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateData()

        swipe_refresh_layout.setOnRefreshListener {
            updateData()
            swipe_refresh_layout.isRefreshing = false
            Toast.makeText(context, "Photos updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData() {
        GlobalScope.launch {
            source.fetchListPhoto()
        }

        source.downloadedListPhoto.observeForever {
            collections_recycler.adapter = PhotosAdapter(it, this::goToDetails)
            collections_recycler.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun goToDetails() {
        val fr = PhotoDetailsFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fr)?.commit()
    }


}