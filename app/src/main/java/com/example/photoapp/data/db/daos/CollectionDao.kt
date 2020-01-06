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

    @Query("SELECT * FROM collections ORDER BY timeUpdated ASC")
    abstract fun getAllCollections(): DataSource.Factory<Int, CollectionResponse>

    suspend fun insertWithTimestamp(collections: List<CollectionResponse>) {
        insertCollections(collections.apply {
            this.forEach { collectionResponse ->
                collectionResponse.timeCreated = System.nanoTime()
                collectionResponse.timeUpdated = System.nanoTime()
            }
        })
    }
}