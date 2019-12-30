package com.example.photoapp.ui.collection.list

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.local.Event
import com.example.photoapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch

class CollectionListViewModel : ScopedViewModel() {

    val collectionListLiveData = MutableLiveData<List<CollectionsListResponse>>()
    val isNetworkErrorHappened = MutableLiveData<Event<Boolean?>>()

    fun fetchCollections() {
        scope.launch(handler) {
            val result = repository.getCollections()
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    collectionListLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }
}