package com.example.photoapp.data.repository

import com.example.photoapp.local.NetworkResult
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): NetworkResult<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}