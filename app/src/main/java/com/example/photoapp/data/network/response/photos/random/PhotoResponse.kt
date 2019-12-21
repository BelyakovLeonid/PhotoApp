package com.example.photoapp.data.network.response.photos.random


import com.example.photoapp.data.network.response.photos.response.Exif
import com.example.photoapp.data.network.response.photos.response.PhotoLinks
import com.example.photoapp.data.network.response.photos.response.PhotoLocation
import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("promoted_at")
    val promotedAt: String,
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
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>,
    val user: User,
    val exif: Exif,
    val location: PhotoLocation,
    val views: Int,
    val downloads: Int
)