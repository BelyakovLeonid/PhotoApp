package com.example.photoapp.data.network.response.photos.detailed

data class PhotoLocation(
    val title: String,
    val name: String,
    val city: String,
    val country: String,
    val position: Position
)