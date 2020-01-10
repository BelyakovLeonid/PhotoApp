package com.example.photoapp.data.db.entities.base

import com.example.photoapp.data.network.response.Links

abstract class BaseResponse {
    abstract val links: Links

    var tag: String = DEFAULT_TAG
    var timeCreated: Long = 0
    var timeUpdated: Long = 0

    companion object {
        const val DEFAULT_TAG = "My_Default_DB_Tag"
    }
}