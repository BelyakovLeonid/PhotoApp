package com.example.photoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoapp.data.network.UnsplashApiService
import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.data.network.response.photos.response.ListResponse
import com.example.photoapp.local.InternetConnectionException

class PhotoDataSource {
    private val apiService = UnsplashApiService()

    private val _downloadedPhoto = MutableLiveData<PhotoResponse>()
    val downloadedPhoto: LiveData<PhotoResponse>
        get() = _downloadedPhoto

    suspend fun fetchRandomPhoto(
        count: Int = 1
    ) {
        try {
            val value = apiService
                .getRandomPhoto(count)
                .await()
                .get(0) //todo а что если хотят несколько?
            _downloadedPhoto.postValue(value)
        } catch (e: InternetConnectionException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedListPhoto = MutableLiveData<List<ListResponse>>()
    val downloadedListPhoto: LiveData<List<ListResponse>>
        get() = _downloadedListPhoto

    suspend fun fetchListPhoto(
        page: Int = 1,
        perPage: Int = 10,
        orderBy: String = "latest"
    ) {
        try {
            val value = apiService
                .getListPhotos(page, perPage, orderBy)
                .await()
            _downloadedListPhoto.postValue(value)
        } catch (e: InternetConnectionException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedCollections = MutableLiveData<List<CollectionsListResponse>>()
    val downloadedCollections: LiveData<List<CollectionsListResponse>>
        get() = _downloadedCollections

    suspend fun fetchCollections(
        page: Int = 1,
        perPage: Int = 10
    ) {
        try {
            val value = apiService
                .getCollections(page, perPage)
                .await()
            _downloadedCollections.postValue(value)
        } catch (e: InternetConnectionException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }


}