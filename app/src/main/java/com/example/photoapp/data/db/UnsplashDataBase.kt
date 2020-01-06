package com.example.photoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photoapp.data.db.daos.CollectionContentDao
import com.example.photoapp.data.db.daos.CollectionDao
import com.example.photoapp.data.db.daos.PhotoDao
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse

@Database(
    entities = [PhotoResponse::class, CollectionResponse::class],
    version = 1,
    exportSchema = false
)
abstract class UnsplashDataBase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun collectionDao(): CollectionDao
    abstract fun collectionContentDao(): CollectionContentDao

    companion object {
        @Volatile
        private var instance: UnsplashDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): UnsplashDataBase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context) =
            Room.inMemoryDatabaseBuilder(
                context,
                UnsplashDataBase::class.java
            ).build()
    }
}