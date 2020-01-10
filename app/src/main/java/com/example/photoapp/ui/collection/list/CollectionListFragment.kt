package com.example.photoapp.ui.collection.list

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Slide
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.local.adapters.CollectionListAdapter
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.collection.detail.CollectionDetailFragment
import kotlinx.android.synthetic.main.fragment_recycler.*

class CollectionListFragment : BaseFragment() {
    lateinit var specialViewModel: CollectionListViewModel
    private val adapter = CollectionListAdapter(this::goToDetails)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view.adapter = adapter
        specialViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)

        specialViewModel.networkErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        specialViewModel.collections.observe(this, Observer {
            adapter.submitList(it)
            progress_group.visibility = View.GONE
            placeholder_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.VISIBLE
        })

        swipe_refresh_layout.setOnRefreshListener {
            updateData()
            swipe_refresh_layout.isRefreshing = false
        }

        placeholder_button.setOnClickListener {
            updateData()
        }

        updateData()
    }

    private fun updateData() {
        specialViewModel.fetchCollections()
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

    private fun goToDetails(collectionSelected: CollectionResponse) {
        commonViewModel.collectionSelected = collectionSelected
        commonViewModel.collectionSelectedId = collectionSelected.id
        val destinationFragment = CollectionDetailFragment().also {
            it.router = router
            it.enterTransition = Slide(Gravity.END).setDuration(300)
        }

        router.navigateTo(destinationFragment)
    }
}
