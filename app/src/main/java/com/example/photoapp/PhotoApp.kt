package com.example.photoapp

import android.app.Application
import android.content.Context

class PhotoApp : Application() {

    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}