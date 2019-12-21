package com.example.photoapp.data.network.response.photos.response


import com.google.gson.annotations.SerializedName

data class PhotoLocation(
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("city")
    val city: Any,
    @SerializedName("country")
    val country: Any,
    @SerializedName("position")
    val position: Position
)