package com.example.photoapp.ui.viewmodels.photo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.local.NetworkResult

class PhotoDetailViewModelFactory(
    private val repositoryCall: suspend (String) -> NetworkResult<PhotoDetailResponse>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoDetailViewModel(repositoryCall) as T
    }
}