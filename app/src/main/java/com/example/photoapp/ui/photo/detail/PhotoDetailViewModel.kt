package com.example.photoapp.ui.photo.detail

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.local.Event
import com.example.photoapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch

class PhotoDetailViewModel : ScopedViewModel() {

    val photoDetailLiveData = MutableLiveData<PhotoResponse>()
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