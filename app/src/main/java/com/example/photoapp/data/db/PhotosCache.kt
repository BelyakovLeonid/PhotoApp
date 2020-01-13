package com.example.photoapp.data.db

import androidx.paging.DataSource
import com.example.photoapp.data.db.daos.CollectionDao
import com.example.photoapp.data.db.daos.PhotoDao
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.db.entities.base.BaseResponse.Companion.DEFAULT_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PhotosCache(
    private val photoDao: PhotoDao,
    private val collectionDao: CollectionDao
) {
    fun clearPhotos(scope: CoroutineScope) = scope.launch {
        photoDao.clearPhotos(DEFAULT_TAG)
    }

    suspend fun insertCollections(
        collections: List<CollectionResponse>,
        insertFinished: () -> Unit,
        tag: String = DEFAULT_TAG
    ) {
        collectionDao.insertWithTimestamp(collections, tag)
        insertFinished()
    }

    fun getCollections(tag: String = DEFAULT_TAG): DataSource.Factory<Int, CollectionResponse> {
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