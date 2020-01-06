package com.example.photoapp.data.db

import androidx.paging.DataSource
import com.example.photoapp.PhotoApp
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse

class PhotosCache {

    private val photoDao = PhotoApp.unsplashDataBase.photoDao()
    private val collectionDao = PhotoApp.unsplashDataBase.collectionDao()
    private val collectionContentDao = PhotoApp.unsplashDataBase.collectionContentDao()

    suspend fun insertPhotos(photos: List<PhotoResponse>, insertFinished: () -> Unit) {
        photoDao.insertWithTimestamp(photos)
        insertFinished()
    }

    fun getAllPhotos(): DataSource.Factory<Int, PhotoResponse> {
        return photoDao.getAllPhotos()
    }

    suspend fun insertCollections(
        collections: List<CollectionResponse>,
        insertFinished: () -> Unit
    ) {
        collectionDao.insertWithTimestamp(collections)
        insertFinished()
    }

    fun getAllCollections(): DataSource.Factory<Int, CollectionResponse> {
        return collectionDao.getAllCollections()
    }

    suspend fun insertCollectionPhotos(
        collectionId: Int,
        photos: List<PhotoResponse>, insertFinished: () -> Unit
    ) {
        collectionContentDao.insertWithTimestamp(collectionId, photos)
        insertFinished()
    }

    fun getCollectionPhotos(collectionId: Int): DataSource.Factory<Int, PhotoResponse> {
        return collectionContentDao.getCollectionPhotos(collectionId)
    }
}