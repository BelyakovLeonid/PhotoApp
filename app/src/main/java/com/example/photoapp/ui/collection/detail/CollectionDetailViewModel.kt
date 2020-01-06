package com.example.photoapp.ui.collection.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.RepositoryListResult
import com.example.photoapp.ui.base.ScopedViewModel

class CollectionDetailViewModel : ScopedViewModel() {
    private val collectionIdLiveData = MutableLiveData<Int>()
    private val photoListResult: LiveData<RepositoryListResult<PhotoResponse>> =
        Transformations.map(collectionIdLiveData) {
            repository.getCollectionPhotos(it, scope)
        }

    val photos: LiveData<PagedList<PhotoResponse>> =
        Transformations.switchMap(photoListResult) {
            it.data
        }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(photoListResult) { it.networkErrors }


    fun fetchPhotos(collectionId: Int) {
        collectionIdLiveData.postValue(collectionId)
    }
}