package com.example.photoapp.data.network.response.collections


import com.google.gson.annotations.SerializedName

data class ProfileImageX(
    @SerializedName("small")
    val small: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("large")
    val large: String
)