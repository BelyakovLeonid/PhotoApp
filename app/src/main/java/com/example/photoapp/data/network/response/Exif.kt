package com.example.photoapp.data.network.response


import com.google.gson.annotations.SerializedName

data class Exif(
    val iso: Int?,
    val make: String?,
    val model: String?,
    val aperture: String?,
    @SerializedName("exposure_time")
    val exposureTime: String?,
    @SerializedName("focal_length")
    val focalLength: String?
)