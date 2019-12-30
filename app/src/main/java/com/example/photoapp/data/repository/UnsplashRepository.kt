package com.example.photoapp.data.repository

import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.UnsplashApiService
import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.data.network.response.photos.random.PhotoResponse
import com.example.photoapp.data.network.response.photos.response.ListResponse

class UnsplashRepository : BaseRepository() {

    private val api = UnsplashApiService.invoke()

    suspend fun getListPhotos(): NetworkResult<List<ListResponse>> = safeApiCall(
        call = { api.getListPhotos(1, 10, "latest") },
        errorMessage = "Error fetching photos"
    )

    suspend fun getCollections(): NetworkResult<List<CollectionsListResponse>> = safeApiCall(
        call = { api.getCollections(1, 10) },
        errorMessage = "Error fetching collections"
    )

    suspend fun getPhoto(id: String): NetworkResult<PhotoResponse> = safeApiCall(
        call = { api.getPhotoById(id) },
        errorMessage = "Error fetching photo"
    )

    suspend fun getCollectionPhotos(id: Int): NetworkResult<List<ListResponse>> = safeApiCall(
        call = { api.getCollectionPhotos(id, 1, 10) },
        errorMessage = "Error fetching collection photos"
    )
}