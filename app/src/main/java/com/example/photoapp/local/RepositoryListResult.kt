package com.example.photoapp.local

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

class RepositoryListResult<T>(
    val data: LiveData<PagedList<T>>,
    val networkErrors: LiveData<String>,
    val emptySource: LiveData<Boolean>? = null
)
