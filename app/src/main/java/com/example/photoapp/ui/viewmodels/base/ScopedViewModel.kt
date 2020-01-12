package com.example.photoapp.ui.viewmodels.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class ScopedViewModel : ViewModel() {
    protected val parentJob = Job()

    protected val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    protected val scope = CoroutineScope(coroutineContext)

    //todo нужно подумать над инжектом

    protected val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("MyTag", "smth wrong")
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}