package com.example.photoapp.data.network.response.photos.response


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("self")
    val self: String,
    @SerializedName("html")
    val html: String,
    @SerializedName("download")
    val download: String,
    @SerializedName("download_location")
    val downloadLocation: String
)