package com.example.photoapp.ui.fragments.photo.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.adapters.PhotoListAdapter
import com.example.photoapp.ui.fragments.base.BaseSearchFragment
import com.example.photoapp.ui.fragments.photo.detail.PhotoDetailFragment
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModel
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModelFactory
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.kodein.di.generic.instance

class SearchPhotoFragment : BaseSearchFragment() {
    private val viewModelFactory: PhotoListViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var photoListViewModel: PhotoListViewModel
    private val adapter = PhotoListAdapter(::goToDetails)
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
        photoListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel::class.java)

        photoListViewModel.photos.observe(this, Observer {
            adapter.submitList(it)

            if (it.isNotEmpty())
                showList()
        })

        photoListViewModel.networkErrors.observe(this, Observer {
            showNetworkError(it.isNotEmpty())
        })

        photoListViewModel.emptySource.observe(this, Observer {
            showEmptyList()
        })

        swipe_refresh_layout.setOnRefreshListener {
            invalidateData()
            swipe_refresh_layout.isRefreshing = false
        }

        commonSearchViewModel.queryLiveData.observe(this, Observer {
            lastQuery = it
            updateData()
        })

        placeholder_button.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        showProgress()
        photoListViewModel.fetchPhotos(lastQuery)
    }

    private fun invalidateData() {
        showProgress()
        photoListViewModel.invalidateData()
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

    private fun goToDetails(sharedElement: View, photoSelected: PhotoResponse) {
        commonViewModel.photoSelected = photoSelected
        commonViewModel.photoSelectedId = photoSelected.id
        val fragment = PhotoDetailFragment().also {
            it.router = router
            it.postponeEnterTransition()

            it.sharedElementEnterTransition = TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
                .setDuration(400)

            exitTransition = Fade()
        }
        router.navigateWithSharedElement(sharedElement, fragment)
    }
}