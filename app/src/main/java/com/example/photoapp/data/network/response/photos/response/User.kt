package com.example.photoapp.data.network.response.photos.response


import com.example.photoapp.data.network.response.photos.random.ProfileImage
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("instagram_username")
    val instagramUsername: String,
    @SerializedName("twitter_username")
    val twitterUsername: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    @SerializedName("links")
    val links: Links
)