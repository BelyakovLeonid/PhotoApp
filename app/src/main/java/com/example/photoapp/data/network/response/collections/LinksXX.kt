package com.example.photoapp.data.network.response.collections


import com.google.gson.annotations.SerializedName

data class LinksXX(
    @SerializedName("self")
    val self: String,
    @SerializedName("html")
    val html: String,
    @SerializedName("photos")
    val photos: String,
    @SerializedName("likes")
    val likes: String,
    @SerializedName("portfolio")
    val portfolio: String
)