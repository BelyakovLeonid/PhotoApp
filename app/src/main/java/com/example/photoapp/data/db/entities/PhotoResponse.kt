package com.example.photoapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoapp.data.network.response.Urls

@Entity(tableName = "photos")
data class PhotoResponse(
    @PrimaryKey
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    @Embedded(prefix = "urls")
    val urls: Urls
)