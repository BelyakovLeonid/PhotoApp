package com.example.photoapp.ui.viewmodels.photo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.RepositoryListResult
import com.example.photoapp.ui.viewmodels.base.ScopedViewModel
import kotlinx.coroutines.CoroutineScope

class PhotoListViewModel(
    private val repositoryCall: (String, CoroutineScope) -> RepositoryListResult<PhotoResponse>
) : ScopedViewModel() {

    private val parameterLiveData = MutableLiveData<String>()
    private val photoListResult: LiveData<RepositoryListResult<PhotoResponse>> =
        Transformations.map(parameterLiveData) {
            repositoryCall(it, scope)
        }

    val photos: LiveData<PagedList<PhotoResponse>> =
        Transformations.switchMap(photoListResult) {
            it.data
        }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(photoListResult) {
            it.networkErrors
        }

    val emptySorce: LiveData<Boolean> =
        Transformations.switchMap(photoListResult) {
            it.emptySource
        }


    fun fetchPhotos(param: String = "") {
        parameterLiveData.postValue(param)
    }
}