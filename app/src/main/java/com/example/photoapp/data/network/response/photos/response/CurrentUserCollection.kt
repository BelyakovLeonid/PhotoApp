package com.example.photoapp.data.network.response.photos.response


import com.google.gson.annotations.SerializedName

data class CurrentUserCollection(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("cover_photo")
    val coverPhoto: Any,
    @SerializedName("user")
    val user: Any
)