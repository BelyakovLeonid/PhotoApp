package com.example.photoapp.data.network.response

import androidx.room.Embedded

data class CoverPhoto(
    val id: String,
    val width: Int,
    val height: Int,
    val color: String,
    @Embedded(prefix = "urls_")
    val urls: Urls
)