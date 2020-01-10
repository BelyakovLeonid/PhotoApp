package com.example.photoapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.RepositoryListResult
import com.example.photoapp.ui.base.ScopedViewModel

class CommonSearchViewModel : ScopedViewModel() {
    private val queryLiveData = MutableLiveData<String>()

    private val collectionListResult: LiveData<RepositoryListResult<CollectionResponse>> =
        Transformations.map(queryLiveData) {
            repository.searchCollections(it, scope)
        }

    val collections: LiveData<PagedList<CollectionResponse>> =
        Transformations.switchMap(collectionListResult) {
            it.data
        }

    val networkCollectionsErrors: LiveData<String> =
        Transformations.switchMap(collectionListResult) { it.networkErrors }

    private val photoListResult: LiveData<RepositoryListResult<PhotoResponse>> =
        Transformations.map(queryLiveData) {
            repository.searchPhotos(it, scope)
        }

    val photos: LiveData<PagedList<PhotoResponse>> =
        Transformations.switchMap(photoListResult) {
            it.data
        }
    val networkPhotosErrors: LiveData<String> =
        Transformations.switchMap(photoListResult) { it.networkErrors }


    fun executeQuery(query: String?) {
        queryLiveData.postValue(query ?: "")
    }
}