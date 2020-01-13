package com.example.photoapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoapp.data.db.entities.base.BaseResponse
import com.example.photoapp.data.network.response.Links
import com.example.photoapp.data.network.response.Urls
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class PhotoResponse(
    @PrimaryKey
    val id: String,
    @SerializedName("created_at_")
    val createdAt: String?,
    @SerializedName("updated_at_")
    val updatedAt: String?,
    val width: Int,
    val height: Int,
    val color: String,
    @Embedded(prefix = "urls_")
    val urls: Urls,
    @Embedded(prefix = "links_")
    val links: Links
) : BaseResponse()