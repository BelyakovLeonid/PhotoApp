package com.example.photoapp.data.network.response

data class SearchResponse<T>(
    val results: List<T>
)