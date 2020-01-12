package com.example.photoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.PhotoDetailResponse

class CommonViewModel : ViewModel() {
    var photoSelectedId: String? = null
    var photoSelected: PhotoResponse? = null
    var photoDetailSelected: PhotoDetailResponse? = null

    var collectionSelectedId: Int? = null
    var collectionSelected: CollectionResponse? = null
}