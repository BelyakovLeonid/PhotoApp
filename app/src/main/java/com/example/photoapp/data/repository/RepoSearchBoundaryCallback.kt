package com.example.photoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.photoapp.data.network.response.SearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RepoSearchBoundaryCallback<T>(
    private val scope: CoroutineScope,
    private val apiCall: suspend (Int, Int) -> Response<SearchResponse<T>>,
    private val cacheCall: suspend (List<T>, () -> Unit) -> Unit
) : PagedList.BoundaryCallback<T>() {

    private var lastRequestedPage = 1
    private var isRequestInProgress = false

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        scope.launch(Dispatchers.IO) {
            requestAndSaveData()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        scope.launch(Dispatchers.IO) {
            requestAndSaveData()
        }
    }

    private suspend fun requestAndSaveData() {
        if (isRequestInProgress) return
        isRequestInProgress = true
        try {
            val response = apiCall(
                lastRequestedPage,
                NETWORK_PAGE_SIZE
            )

            if (response.isSuccessful) {
                cacheCall(response.body()!!.results) {
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

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}