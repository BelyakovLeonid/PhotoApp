package com.example.photoapp.data.network.response.photos.response


import com.example.photoapp.data.network.response.photos.random.Urls
import com.example.photoapp.data.network.response.photos.random.User
import com.google.gson.annotations.SerializedName

data class ListResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
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
    @SerializedName("current_user_collections")
    val currentUserCollections: List<CurrentUserCollection>,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("links")
    val links: LinksX
)