package com.example.photoapp.ui.photo.detail

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.local.Event
import com.example.photoapp.local.NetworkResult
import com.example.photoapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch

class PhotoDetailViewModel : ScopedViewModel() {

    val photoDetailLiveData = MutableLiveData<PhotoDetailResponse>()
    val isNetworkErrorHappened = MutableLiveData<Event<Boolean?>>()

    fun fetchSinglePhoto(id: String) {
        scope.launch(handler) {
            val result = repository.getPhoto(id)
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