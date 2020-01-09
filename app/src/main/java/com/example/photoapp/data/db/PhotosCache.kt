package com.example.photoapp.data.db

import androidx.paging.DataSource
import com.example.photoapp.PhotoApp
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.db.entities.PhotoResponse.Companion.DEFAULT_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PhotosCache {

    private val photoDao = PhotoApp.unsplashDataBase.photoDao()
    private val collectionDao = PhotoApp.unsplashDataBase.collectionDao()

    fun clearPhotos(scope: CoroutineScope) = scope.launch {
        photoDao.clearPhotos()
    }

    suspend fun insertCollections(
        collections: List<CollectionResponse>,
        insertFinished: () -> Unit,
        tag: String = DEFAULT_TAG
    ) {
        collectionDao.insertWithTimestamp(collections, tag)
        insertFinished()
    }

    fun getAllCollections(tag: String = DEFAULT_TAG): DataSource.Factory<Int, CollectionResponse> {
        return collectionDao.getAllCollections(tag)
    }

    suspend fun insertPhotos(
        photos: List<PhotoResponse>,
        insertFinished: () -> Unit,
        tag: String = DEFAULT_TAG
    ) {
        photoDao.insertWithTimestamp(photos, tag)
        insertFinished()
    }

    fun getPhotos(tag: String = DEFAULT_TAG): DataSource.Factory<Int, PhotoResponse> {
        return photoDao.getPhotos(tag)
    }
}