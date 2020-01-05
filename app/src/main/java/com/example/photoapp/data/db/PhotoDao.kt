package com.example.photoapp.data.db

import androidx.room.*
import com.example.photoapp.data.db.entities.PhotoResponse

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun persistPhoto(photo: PhotoResponse)

    @Update
    suspend fun updatePhoto(photo: PhotoResponse)

    @Delete
    suspend fun deletePhoto(photo: PhotoResponse)

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos(): List<PhotoResponse>
}