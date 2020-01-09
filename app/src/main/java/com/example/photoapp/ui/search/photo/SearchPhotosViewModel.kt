package com.example.photoapp.ui.search.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.RepositoryListResult
import com.example.photoapp.ui.base.ScopedViewModel

class SearchPhotosViewModel : ScopedViewModel() {

    private val sortByLiveData = MutableLiveData<String>()
    private val photoListResult: LiveData<RepositoryListResult<PhotoResponse>> =
        Transformations.map(sortByLiveData) {
            repository.getPhotosList(it, scope)
        }

    val photos: LiveData<PagedList<PhotoResponse>> =
        Transformations.switchMap(photoListResult) {
            it.data
        }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(photoListResult) { it.networkErrors }


    fun fetchPhotos(sortBy: String = "latest") {
        sortByLiveData.postValue(sortBy)
    }
}