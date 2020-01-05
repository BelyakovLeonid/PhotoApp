package com.example.photoapp

import android.app.Application
import com.example.photoapp.data.db.PhotoDataBase
import com.example.photoapp.data.network.UnsplashApiService

class PhotoApp : Application() {

    companion object {
        lateinit var photoDataBase: PhotoDataBase
        lateinit var apiService: UnsplashApiService
    }

    override fun onCreate() {
        super.onCreate()
        photoDataBase = PhotoDataBase(applicationContext)
        apiService = UnsplashApiService()
    }
}