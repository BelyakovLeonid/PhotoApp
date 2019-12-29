package com.example.photoapp.data.repository

import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.UnsplashApiService
import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.data.network.response.photos.response.ListResponse

class UnsplashRepository() : BaseRepository() {

    private val api = UnsplashApiService.invoke()

    suspend fun getListPhotos(): NetworkResult<List<ListResponse>> = safeApiCall(
        call = { api.getListPhotos(1, 10, "latest") },
        errorMessage = "Error Fetching Photos"
    )

    suspend fun getCollections(): NetworkResult<List<CollectionsListResponse>> = safeApiCall(
        call = { api.getCollections(1, 10) },
        errorMessage = "Error Fetching Collections"
    )

    suspend fun getPhoto(): NetworkResult<List<PhotoResponse>> = safeApiCall(
        call = { api.getRandomPhoto(1) },
        errorMessage = "Error Fetching Collections"
    )
}