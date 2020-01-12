package com.example.photoapp.ui.viewmodels.photo.detail

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.local.Event
import com.example.photoapp.local.NetworkResult
import com.example.photoapp.ui.viewmodels.base.ScopedViewModel
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    private val repositoryCall: suspend (String) -> NetworkResult<PhotoDetailResponse>
) : ScopedViewModel() {

    val photoDetailLiveData = MutableLiveData<PhotoDetailResponse>()
    val isNetworkErrorHappened = MutableLiveData<Event<Boolean?>>()

    fun fetchSinglePhoto(id: String = "") {
        scope.launch(handler) {
            val result = repositoryCall(id)
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    photoDetailLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }
}