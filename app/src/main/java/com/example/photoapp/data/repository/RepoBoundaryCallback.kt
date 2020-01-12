package com.example.photoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class RepoBoundaryCallback<T>(
    private val scope: CoroutineScope,
    private val apiCall: suspend (Int, Int) -> Response<List<T>>,
    private val cacheCall: suspend (List<T>, () -> Unit) -> Unit
) : PagedList.BoundaryCallback<T>() {

    private var lastRequestedPage = 1
    private var isRequestInProgress = false

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        // Fetch data synchronously (parameter is set to true)
        // load an initial data set so the paged list is not empty.
        // See https://issuetracker.google.com/u/2/issues/110843692?pli=1
        requestAndSaveData(true)
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        requestAndSaveData()
    }

    private fun requestAndSaveData(isSynchronously: Boolean = false) {
        if (isRequestInProgress) return
        isRequestInProgress = true

        scope.launch {
            try {
                val response =
                    apiCall(
                        lastRequestedPage,
                        NETWORK_PAGE_SIZE
                    )

                if (response.isSuccessful) {
                    cacheCall(response.body()!!) {
                        lastRequestedPage++
                        isRequestInProgress = false
                    }
                } else {
                    Log.d("MyTag", "unsuccess - ${response.message()}")
                    _networkErrors.postValue("Error when fetching data")
                    isRequestInProgress = false
                }
            } catch (e: Exception) {
                Log.d("MyTag", "exception - ${e.message}")
                _networkErrors.postValue("Error when fetching data")
                isRequestInProgress = false
            }
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}