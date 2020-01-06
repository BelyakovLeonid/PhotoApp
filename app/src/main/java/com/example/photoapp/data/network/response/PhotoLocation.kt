package com.example.photoapp.data.network.response

data class PhotoLocation(
    val title: String,
    val name: String,
    val city: String,
    val country: String,
    val position: Position
)