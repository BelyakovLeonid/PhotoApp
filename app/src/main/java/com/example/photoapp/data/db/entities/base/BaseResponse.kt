package com.example.photoapp.data.db.entities.base

abstract class BaseResponse {
    var tag: String = DEFAULT_TAG
    var timeCreated: Long = 0
    var timeUpdated: Long = 0

    companion object {
        const val DEFAULT_TAG = "My_Default_DB_Tag"
    }
}