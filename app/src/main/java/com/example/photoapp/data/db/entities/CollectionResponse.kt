package com.example.photoapp.data.db.entities


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoapp.data.db.entities.base.BaseResponse
import com.example.photoapp.data.network.response.CoverPhoto
import com.example.photoapp.data.network.response.Links
import com.example.photoapp.data.network.response.User
import com.google.gson.annotations.SerializedName

@Entity(tableName = "collections")
data class CollectionResponse(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val description: String?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("share_key")
    val shareKey: String,
    @Embedded(prefix = "cover_photo_")
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto?,
    @Embedded(prefix = "user_")
    val user: User?,
    @Embedded(prefix = "links_")
    val links: Links
) : BaseResponse()