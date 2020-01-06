package com.example.photoapp.data.network.response


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val name: String,
    val username: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @Embedded
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    @Embedded
    val links: Links
)