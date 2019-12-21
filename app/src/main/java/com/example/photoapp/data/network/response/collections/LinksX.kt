package com.example.photoapp.data.network.response.collections


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("self")
    val self: String,
    @SerializedName("html")
    val html: String,
    @SerializedName("download")
    val download: String
)