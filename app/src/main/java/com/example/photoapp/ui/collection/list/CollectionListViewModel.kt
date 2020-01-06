package com.example.photoapp.ui.collection.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.local.RepositoryListResult
import com.example.photoapp.ui.base.ScopedViewModel

class CollectionListViewModel : ScopedViewModel() {

    private val sortByLiveData = MutableLiveData<String>()
    private val collectionListResult: LiveData<RepositoryListResult<CollectionResponse>> =
        Transformations.map(sortByLiveData) {
            repository.getCollectionsList(scope)
        }

    val collections: LiveData<PagedList<CollectionResponse>> =
        Transformations.switchMap(collectionListResult) {
            it.data
        }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(collectionListResult) { it.networkErrors }

    fun fetchCollections(sortBy: String = "latest") { //todo
        sortByLiveData.postValue(sortBy)
    }
}