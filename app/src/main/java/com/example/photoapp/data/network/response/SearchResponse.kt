package com.example.photoapp.data.network.response

import com.example.photoapp.data.db.entities.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SearchResponse<out T : BaseResponse>(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<T>
)