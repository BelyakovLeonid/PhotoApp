package com.example.photoapp.data.repository

import androidx.paging.LivePagedListBuilder
import com.example.photoapp.PhotoApp
import com.example.photoapp.data.db.PhotosCache
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.local.NetworkResult
import com.example.photoapp.local.RepositoryListResult
import kotlinx.coroutines.CoroutineScope

class UnsplashRepository : BaseRepository() {

    private val apiService = PhotoApp.unsplashApiService
    private val cache = PhotosCache()

    fun getPhotosList(sortBy: String, scope: CoroutineScope): RepositoryListResult<PhotoResponse> {
        val boundaryCallback =
            RepoBoundaryCallback(
                scope,
                { page, perPage -> apiService.getListPhotos(page, perPage, sortBy) },
                { photos, callback -> cache.insertPhotos(photos, callback) }
            )
        val networkErrors = boundaryCallback.networkErrors

        val dataSourceFactory = cache.getAllPhotos()
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(
            data,
            networkErrors
        )
    }

    fun getCollectionsList(scope: CoroutineScope): RepositoryListResult<CollectionResponse> {
        val boundaryCallback =
            RepoBoundaryCallback(
                scope,
                { page, perPage -> apiService.getListCollections(page, perPage) },
                { collections, callback -> cache.insertCollections(collections, callback) }
            )
        val networkErrors = boundaryCallback.networkErrors

        val dataSourceFactory = cache.getAllCollections()
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(
            data,
            networkErrors
        )
    }

    fun getCollectionPhotos(
        collectionId: Int,
        scope: CoroutineScope
    ): RepositoryListResult<PhotoResponse> {
        val boundaryCallback =
            RepoBoundaryCallback(
                scope,
                { page, perPage -> apiService.getCollectionPhotos(collectionId, page, perPage) },
                { photos, callback -> cache.insertCollectionPhotos(collectionId, photos, callback) }
            )
        val networkErrors = boundaryCallback.networkErrors

        val dataSourceFactory = cache.getCollectionPhotos(collectionId)
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(
            data,
            networkErrors
        )
    }

    suspend fun getPhoto(id: String): NetworkResult<PhotoDetailResponse> = safeApiCall(
        call = { apiService.getPhotoById(id) },
        errorMessage = "Error fetching photo"
    )

    companion object {
        private const val DB_PAGE_SIZE = 20
    }
}