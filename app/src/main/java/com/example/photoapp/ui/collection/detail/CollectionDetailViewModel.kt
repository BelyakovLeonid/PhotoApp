package com.example.photoapp.ui.collection.detail

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.response.photos.response.ListResponse
import com.example.photoapp.local.Event
import com.example.photoapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch

class CollectionDetailViewModel : ScopedViewModel() {

    val collectionPhotosLiveData = MutableLiveData<List<ListResponse>>()
    val isNetworkErrorHappened = MutableLiveData<Event<Boolean?>>()

    fun fetchCollectionPhotos(id: Int) {
        scope.launch(handler) {
            val result = repository.getCollectionPhotos(id)
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    collectionPhotosLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }
}