package com.example.photoapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoapp.data.network.response.Urls
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class PhotoResponse(
    @PrimaryKey
    val id: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val width: Int,
    val height: Int,
    val color: String,
    @Embedded(prefix = "urls")
    val urls: Urls
) {
    var collectionId: Int = -1
    var timeCreated: Long = 0
    var timeUpdated: Long = 0
}