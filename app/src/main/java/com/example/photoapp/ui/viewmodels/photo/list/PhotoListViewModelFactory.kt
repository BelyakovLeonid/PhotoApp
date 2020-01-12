package com.example.photoapp.ui.viewmodels.photo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.RepositoryListResult
import kotlinx.coroutines.CoroutineScope

class PhotoListViewModelFactory(
    private val repositoryCall: (String, CoroutineScope) -> RepositoryListResult<PhotoResponse>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoListViewModel(repositoryCall) as T
    }
}