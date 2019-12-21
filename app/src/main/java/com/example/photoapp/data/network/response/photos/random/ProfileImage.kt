package com.example.photoapp.data.network.response.photos.random


import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("small")
    val small: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("large")
    val large: String
)