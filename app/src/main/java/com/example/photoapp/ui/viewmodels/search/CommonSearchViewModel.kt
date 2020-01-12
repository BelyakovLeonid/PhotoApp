package com.example.photoapp.ui.viewmodels.search

import androidx.lifecycle.MutableLiveData
import com.example.photoapp.ui.viewmodels.base.ScopedViewModel

class CommonSearchViewModel : ScopedViewModel() {
    val queryLiveData = MutableLiveData<String>()

    fun executeQuery(query: String? = queryLiveData.value) {
        queryLiveData.postValue(query ?: "")
    }
}