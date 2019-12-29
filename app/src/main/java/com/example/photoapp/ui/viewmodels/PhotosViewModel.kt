package com.example.photoapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.data.network.response.photos.response.ListResponse
import com.example.photoapp.data.repository.UnsplashRepository
import com.example.photoapp.local.Event
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PhotosViewModel : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: UnsplashRepository = UnsplashRepository()

    val photosListLiveData = MutableLiveData<List<ListResponse>>()
    val collectionsLiveData = MutableLiveData<List<CollectionsListResponse>>()
    val photoSingleLiveData = MutableLiveData<List<PhotoResponse>>()
    val isNetworkErrorHappened = MutableLiveData<Event<Boolean?>>()

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("MyTag", "smth wrong")
    }

    fun fetchPhotos() {
        scope.launch(handler) {
            val result = repository.getListPhotos()
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    photosListLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }

    fun fetchCollections() {
        scope.launch(handler) {
            val result = repository.getCollections()
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    collectionsLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }

    fun fetchSinglePhoto() {
        scope.launch(handler) {
            val result = repository.getPhoto()
            when (result) {
                is NetworkResult.Success -> {
                    isNetworkErrorHappened.postValue(Event(false))
                    photoSingleLiveData.postValue(result.data)
                }
                is NetworkResult.Error ->
                    isNetworkErrorHappened.postValue(Event(true))
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}