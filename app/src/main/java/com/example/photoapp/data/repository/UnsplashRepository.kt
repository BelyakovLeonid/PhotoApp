package com.example.photoapp.data.repository

import androidx.paging.LivePagedListBuilder
import com.example.photoapp.data.db.PhotosCache
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.UnsplashApiService
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.local.NetworkResult
import com.example.photoapp.local.RepositoryListResult
import kotlinx.coroutines.CoroutineScope

class UnsplashRepository(
    private val apiService: UnsplashApiService,
    private val cache: PhotosCache
) : BaseRepository() {

    fun getPhotosList(sortBy: String, scope: CoroutineScope): RepositoryListResult<PhotoResponse> {
        val boundaryCallback =
            RepoBoundaryCallback(
                scope,
                { page, perPage -> apiService.getListPhotos(page, perPage, sortBy) },
                { photos, callback -> cache.insertPhotos(photos, callback) }
            )
        val networkErrors = boundaryCallback.networkErrors
        val emptySource = boundaryCallback.emptySource
        val dataSourceFactory = cache.getPhotos()
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(data, networkErrors, emptySource)
    }

    fun getCollectionsList(
        sortBy: String,
        scope: CoroutineScope
    ): RepositoryListResult<CollectionResponse> {
        val boundaryCallback =
            RepoBoundaryCallback(
                scope,
                { page, perPage -> apiService.getListCollections(page, perPage) },
                { collections, callback -> cache.insertCollections(collections, callback) }
            )
        val networkErrors = boundaryCallback.networkErrors
        val emptySource = boundaryCallback.emptySource

        val dataSourceFactory = cache.getCollections()
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(data, networkErrors, emptySource)
    }

    fun getCollectionPhotos(
        collectionId: String,
        scope: CoroutineScope
    ): RepositoryListResult<PhotoResponse> {
        val boundaryCallback = RepoBoundaryCallback(
            scope,
            { page, perPage ->
                apiService.getCollectionPhotos(
                    Integer.parseInt(collectionId),
                    page,
                    perPage
                )
            },

            { photos, callback ->
                cache.insertPhotos(
                    photos,
                    callback,
                    collectionId
                )
            }
        )
        val networkErrors = boundaryCallback.networkErrors
        val emptySource = boundaryCallback.emptySource

        val dataSourceFactory = cache.getPhotos(collectionId)
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(data, networkErrors, emptySource)
    }

    fun searchPhotos(query: String, scope: CoroutineScope): RepositoryListResult<PhotoResponse> {
        val boundaryCallback =
            RepoSearchBoundaryCallback(
                scope,
                { page, perPage -> apiService.searchPhotos(query, page, perPage) },
                { photos, callback -> cache.insertPhotos(photos, callback, query) }
            )
        val networkErrors = boundaryCallback.networkErrors
        val emptySource = boundaryCallback.emptySource

        val dataSourceFactory = cache.getPhotos(query)
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(data, networkErrors, emptySource)
    }

    fun searchCollections(
        query: String,
        scope: CoroutineScope
    ): RepositoryListResult<CollectionResponse> {
        val boundaryCallback =
            RepoSearchBoundaryCallback(
                scope,
                { page, perPage -> apiService.searchCollections(query, page, perPage) },
                { collections, callback -> cache.insertCollections(collections, callback, query) }
            )
        val networkErrors = boundaryCallback.networkErrors
        val emptySource = boundaryCallback.emptySource

        val dataSourceFactory = cache.getCollections(query)
        val data = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepositoryListResult(data, networkErrors, emptySource)
    }

    suspend fun getPhoto(id: String): NetworkResult<PhotoDetailResponse> = safeApiCall(
        call = { apiService.getPhotoById(id) },
        errorMessage = "Error fetching photo"
    )

    suspend fun getRandomPhoto(dummy: String): NetworkResult<PhotoDetailResponse> = safeApiCall(
        call = { apiService.getRandomPhoto() },
        errorMessage = "Error fetching photo"
    )

    companion object {
        private const val DB_PAGE_SIZE = 20
    }
}