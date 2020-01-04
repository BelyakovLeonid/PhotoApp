package com.example.photoapp.ui

import com.example.photoapp.data.network.response.collections.CollectionsListResponse
import com.example.photoapp.data.network.response.photos.response.ListResponse
import com.example.photoapp.ui.base.ScopedViewModel

class CommonViewModel : ScopedViewModel() {
    var photoSelectedId: String? = null
    var photoSelected: ListResponse? = null

    var collectionSelectedId: Int? = null
    var collectionSelected: CollectionsListResponse? = null
}