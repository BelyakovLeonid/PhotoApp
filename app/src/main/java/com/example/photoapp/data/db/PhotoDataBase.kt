package com.example.photoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photoapp.data.db.entities.PhotoResponse

@Database(
    entities = [PhotoResponse::class],
    version = 1
)
abstract class PhotoDataBase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: PhotoDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): PhotoDataBase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context) =
            Room.inMemoryDatabaseBuilder(
                context,
                PhotoDataBase::class.java
            ).build()
    }
}