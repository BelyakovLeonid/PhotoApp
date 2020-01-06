package com.example.photoapp.data.db.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photoapp.data.db.entities.PhotoResponse

@Dao
abstract class PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPhotos(photos: List<PhotoResponse>)

    @Query("SELECT * FROM photos WHERE collectionId = -1 ORDER BY timeUpdated ASC")
    abstract fun getAllPhotos(): DataSource.Factory<Int, PhotoResponse>

    suspend fun insertWithTimestamp(photos: List<PhotoResponse>) {
        insertPhotos(photos.apply {
            this.forEach { photoResponse ->
                photoResponse.timeCreated = System.nanoTime()
                photoResponse.timeUpdated = System.nanoTime()
                photoResponse.collectionId = -1
            }
        })
    }
}