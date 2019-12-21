package com.example.photoapp.data.network.response.collections


import com.google.gson.annotations.SerializedName

data class CoverPhoto(
    @SerializedName("id")
    val id: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("links")
    val links: LinksX
)