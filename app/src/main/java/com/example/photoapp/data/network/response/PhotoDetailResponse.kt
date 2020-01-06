package com.example.photoapp.data.network.response


import com.google.gson.annotations.SerializedName

data class PhotoDetailResponse(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val description: Any,
    @SerializedName("alt_description")
    val altDescription: String,
    val urls: Urls,
    val links: PhotoLinks,
    val categories: List<Any>,
    val likes: Int,
    val user: User,
    val exif: Exif,
    val location: PhotoLocation,
    val views: Int,
    val downloads: Int
)