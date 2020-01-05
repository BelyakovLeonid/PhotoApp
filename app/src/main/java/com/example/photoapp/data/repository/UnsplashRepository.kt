package com.example.photoapp.data.repository

import com.example.photoapp.PhotoApp
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.NetworkResult
import com.example.photoapp.data.network.response.collections.CollectionResponse
import com.example.photoapp.data.network.response.photos.detailed.PhotoDetailResponse

class UnsplashRepository : BaseRepository() {

    private val apiService = PhotoApp.apiService
    private val dataBase = PhotoApp.photoDataBase

    private var isRequestInProgress = false

    suspend fun getListPhotos(
        page: Int = 1,
        per_page: Int = 10,
        order: String = "latest"
    ): NetworkResult<List<PhotoResponse>> = safeApiCall(
        call = { apiService.getListPhotos(page, per_page, order) },
        errorMessage = "Error fetching photos"
    )

    suspend fun getCollections(): NetworkResult<List<CollectionResponse>> = safeApiCall(
        call = { apiService.getCollections(1, 10) },
        errorMessage = "Error fetching collections"
    )

    suspend fun getPhoto(id: String): NetworkResult<PhotoDetailResponse> = safeApiCall(
        call = { apiService.getPhotoById(id) },
        errorMessage = "Error fetching photo"
    )

    suspend fun getCollectionPhotos(id: Int): NetworkResult<List<PhotoResponse>> = safeApiCall(
        call = { apiService.getCollectionPhotos(id, 1, 10) },
        errorMessage = "Error fetching collection photos"
    )
}