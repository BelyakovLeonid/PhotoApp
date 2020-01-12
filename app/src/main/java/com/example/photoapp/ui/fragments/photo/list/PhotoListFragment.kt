package com.example.photoapp.ui.fragments.photo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.adapters.PhotoListAdapter
import com.example.photoapp.ui.fragments.base.BaseFragment
import com.example.photoapp.ui.fragments.photo.detail.PhotoDetailFragment
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModel
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModelFactory
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.kodein.di.generic.instance


class PhotoListFragment : BaseFragment() {
    private val viewModelFactory: PhotoListViewModelFactory by instance(tag = this.javaClass.name)
    private lateinit var specialViewModel: PhotoListViewModel
    private val adapter = PhotoListAdapter(this::goToDetails)

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
        specialViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel::class.java)

        specialViewModel.photos.observe(this, Observer {
            adapter.submitList(it)

            if (it.isEmpty()) {
                showEmptyList()
            } else {
                showList()
            }
        })

        specialViewModel.networkErrors.observe(this, Observer {
            error(it.isNotEmpty())
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
        specialViewModel.fetchPhotos()
    }

    private fun showList() {
        swipe_refresh_layout.visibility = View.VISIBLE
        placeholder_empty_group.visibility = View.GONE
        placeholder_group.visibility = View.GONE
        progress_group.visibility = View.GONE
    }

    private fun showProgress(inProgress: Boolean) {
        if (inProgress) {
            progress_group.visibility = View.VISIBLE
            placeholder_group.visibility = View.GONE
            placeholder_empty_group.visibility = View.GONE
            swipe_refresh_layout.visibility = View.GONE
        } else {
            progress_group.visibility = View.GONE
        }
    }

    private fun showEmptyList() { //проблема в том, что data source при инициализации всегда отправляет пустой лист
        placeholder_empty_group.visibility = View.VISIBLE
        swipe_refresh_layout.visibility = View.GONE
        placeholder_group.visibility = View.GONE
        progress_group.visibility = View.GONE
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