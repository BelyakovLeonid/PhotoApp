package com.example.photoapp.ui

import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.collections.CollectionResponse
import com.example.photoapp.ui.base.ScopedViewModel

class CommonViewModel : ScopedViewModel() {
    var photoSelectedId: String? = null
    var photoSelected: PhotoResponse? = null

    var collectionSelectedId: Int? = null
    var collectionSelected: CollectionResponse? = null
}