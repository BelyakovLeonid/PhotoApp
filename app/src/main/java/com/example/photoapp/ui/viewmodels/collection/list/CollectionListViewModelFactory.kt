package com.example.photoapp.ui.viewmodels.collection.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.local.RepositoryListResult
import kotlinx.coroutines.CoroutineScope

class CollectionListViewModelFactory(
    private val repositoryCall: (String, CoroutineScope) -> RepositoryListResult<CollectionResponse>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CollectionListViewModel(repositoryCall) as T
    }
}