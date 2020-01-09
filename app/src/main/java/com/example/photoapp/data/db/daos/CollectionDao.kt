package com.example.photoapp.data.db.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photoapp.data.db.entities.CollectionResponse

@Dao
abstract class CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCollections(collections: List<CollectionResponse>)

    @Query("SELECT * FROM collections WHERE tag = :tag ORDER BY timeUpdated ASC")
    abstract fun getAllCollections(tag: String): DataSource.Factory<Int, CollectionResponse>

    suspend fun insertWithTimestamp(collections: List<CollectionResponse>, tag: String) {
        insertCollections(collections.apply {
            this.forEach { collectionResponse ->
                collectionResponse.timeCreated = System.nanoTime()
                collectionResponse.timeUpdated = System.nanoTime()
                collectionResponse.tag = tag
            }
        })
    }

    @Query("DELETE FROM collections")
    abstract suspend fun clearCollections()
}