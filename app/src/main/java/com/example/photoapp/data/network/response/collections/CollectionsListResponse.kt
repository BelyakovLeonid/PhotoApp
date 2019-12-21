package com.example.photoapp.data.network.response.collections


import com.google.gson.annotations.SerializedName

data class CollectionsListResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("share_key")
    val shareKey: String,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
    @SerializedName("user")
    val user: UserX,
    @SerializedName("links")
    val links: LinksXXX
)