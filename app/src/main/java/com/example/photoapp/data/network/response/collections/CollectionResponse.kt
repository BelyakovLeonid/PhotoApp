package com.example.photoapp.data.network.response.collections


import com.example.photoapp.data.network.response.User
import com.google.gson.annotations.SerializedName

data class CollectionResponse(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("share_key")
    val shareKey: String,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
    val user: User,
    val links: Links
)