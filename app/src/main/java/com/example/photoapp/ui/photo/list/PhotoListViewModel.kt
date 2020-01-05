package com.example.photoapp.ui.photo.list

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.local.Event
import com.example.photoapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch

class PhotoListViewModel : ScopedViewModel() {

    val photoListLiveData = MutableLiveData<List<PhotoResponse>>()
    val isNetworkErrorHappened = MutableLiveData<Event<Boolean?>>()

    fun fetchPhotos() {
        scope.launch(handler) {
            val result = repository.getListPhotos()
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    photoListLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }
}