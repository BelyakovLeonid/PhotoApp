package com.example.photoapp.data.network.response.collections

import com.example.photoapp.data.network.response.Urls

data class CoverPhoto(
    val id: String,
    val width: Int,
    val height: Int,
    val color: String,
    val urls: Urls
)