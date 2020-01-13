package com.example.photoapp.ui.fragments.collection.list

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
import com.example.photoapp.ui.fragments.base.BaseFragment
import com.example.photoapp.ui.fragments.collection.detail.CollectionDetailFragment
import com.example.photoapp.ui.viewmodels.collection.list.CollectionListViewModel
import com.example.photoapp.ui.viewmodels.collection.list.CollectionListViewModelFactory
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.kodein.di.generic.instance

class CollectionListFragment : BaseFragment() {
    private val viewModelFactory: CollectionListViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var specialViewModel: CollectionListViewModel
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
        specialViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CollectionListViewModel::class.java)

        specialViewModel.collections.observe(this, Observer {
            adapter.submitList(it)

            if (it.isNotEmpty())
                showList()
        })

        specialViewModel.networkErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        specialViewModel.emptySource.observe(this, Observer {
            showEmptyList()
        })

        swipe_refresh_layout.setOnRefreshListener {
            invalidateData()
            swipe_refresh_layout.isRefreshing = false
        }

        placeholder_button.setOnClickListener {
            updateData()
        }

        updateData()
    }


    private fun updateData() {
        showProgress()
        specialViewModel.fetchCollections()
    }

    private fun invalidateData() {
        showProgress()
        specialViewModel.invalidateData()
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
            Toast.makeText(
                context,
                resources.getString(R.string.toast_photos_updated),
                Toast.LENGTH_SHORT
            ).show()
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
