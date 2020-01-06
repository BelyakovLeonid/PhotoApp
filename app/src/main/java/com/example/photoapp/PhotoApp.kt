package com.example.photoapp

import android.app.Application
import com.example.photoapp.data.db.UnsplashDataBase
import com.example.photoapp.data.network.UnsplashApiService

class PhotoApp : Application() {

    companion object {
        lateinit var unsplashDataBase: UnsplashDataBase
        lateinit var unsplashApiService: UnsplashApiService
    }

    override fun onCreate() {
        super.onCreate()
        unsplashDataBase = UnsplashDataBase(applicationContext)
        unsplashApiService = UnsplashApiService()
    }
}