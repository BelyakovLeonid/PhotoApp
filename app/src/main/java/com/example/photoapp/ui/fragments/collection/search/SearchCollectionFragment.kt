package com.example.photoapp.ui.fragments.collection.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.local.adapters.CollectionListAdapter
import com.example.photoapp.ui.fragments.base.BaseSearchFragment
import com.example.photoapp.ui.fragments.collection.detail.CollectionDetailFragment
import com.example.photoapp.ui.viewmodels.collection.list.CollectionListViewModel
import com.example.photoapp.ui.viewmodels.collection.list.CollectionListViewModelFactory
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.kodein.di.generic.instance


class SearchCollectionFragment : BaseSearchFragment() {
    private val viewModelFactory: CollectionListViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var collectionListViewModel: CollectionListViewModel
    private val adapter = CollectionListAdapter(this::goToDetails)
    private var lastQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress_group.visibility = View.GONE
        placeholder_group.visibility = View.GONE
        swipe_refresh_layout.visibility = View.GONE
        placeholder_empty_group.visibility = View.VISIBLE

        recycler_view.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        collectionListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CollectionListViewModel::class.java)

        collectionListViewModel.collections.observe(this, Observer {
            adapter.submitList(it)

            if (it.isNotEmpty())
                showList()
        })

        collectionListViewModel.networkErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        collectionListViewModel.emptySource.observe(this, Observer {
            showEmptyList()
        })

        commonSearchViewModel.queryLiveData.observe(this, Observer {
            lastQuery = it
            updateData()
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
        showProgress()
        collectionListViewModel.fetchCollections(lastQuery)
    }

    private fun showProgress() {
        progress_group.visibility = View.VISIBLE
        placeholder_group.visibility = View.GONE
        placeholder_empty_group.visibility = View.GONE
        swipe_refresh_layout.visibility = View.GONE
    }

    private fun showList() {
        swipe_refresh_layout.visibility = View.VISIBLE
        placeholder_empty_group.visibility = View.GONE
        placeholder_group.visibility = View.GONE
        progress_group.visibility = View.GONE
    }

    private fun showEmptyList() {
        placeholder_empty_group.visibility = View.VISIBLE
        swipe_refresh_layout.visibility = View.GONE
        placeholder_group.visibility = View.GONE
        progress_group.visibility = View.GONE
    }

    private fun showNetworkError(isError: Boolean?) {
        if (isError == null) return

        if (isError) {
            placeholder_group.visibility = View.VISIBLE
            placeholder_empty_group.visibility = View.GONE
            progress_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.GONE
        } else {
            placeholder_group.visibility = View.GONE
            Toast.makeText(context, "Photos updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToDetails(collectionSelected: CollectionResponse) {
        commonViewModel.collectionSelected = collectionSelected
        commonViewModel.collectionSelectedId = collectionSelected.id
        val destinationFragment = CollectionDetailFragment().also { it.router = router }

        router.navigateTo(destinationFragment)
    }
}
