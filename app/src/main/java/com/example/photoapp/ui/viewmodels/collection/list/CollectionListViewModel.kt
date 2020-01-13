package com.example.photoapp.ui.viewmodels.collection.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.local.RepositoryListResult
import com.example.photoapp.ui.viewmodels.base.ScopedViewModel
import kotlinx.coroutines.CoroutineScope

class CollectionListViewModel(
    private val repositoryCall: (String, CoroutineScope) -> RepositoryListResult<CollectionResponse>
) : ScopedViewModel() {

    private val sortByLiveData = MutableLiveData<String>()
    private val collectionListResult: LiveData<RepositoryListResult<CollectionResponse>> =
        Transformations.map(sortByLiveData) {
            repositoryCall(it, scope)
        }

    val collections: LiveData<PagedList<CollectionResponse>> =
        Transformations.switchMap(collectionListResult) {
            it.data
        }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(collectionListResult) { it.networkErrors }

    val emptySource: LiveData<Boolean> =
        Transformations.switchMap(collectionListResult) { it.emptySource }

    fun fetchCollections(sortBy: String = "latest") {
        sortByLiveData.postValue(sortBy)
    }

    fun invalidateData() {
        collections.value?.dataSource?.invalidate()
    }
}