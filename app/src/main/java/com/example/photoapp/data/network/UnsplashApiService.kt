package com.example.photoapp.data.network

import com.example.photoapp.BuildConfig
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.data.network.response.PhotoDetailResponse
import com.example.photoapp.data.network.response.SearchResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.unsplash.com/
//https://api.unsplash.com/photos/?client_id=YOUR_ACCESS_KEY
//GET /photos/random
//Header Accept-Version: v1

interface UnsplashApiService {

    @GET("photos/random")
    suspend fun getRandomPhoto(): Response<PhotoDetailResponse>

    @GET("photos")
    suspend fun getListPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") order_by: String
    ): Response<List<PhotoResponse>>

    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") photoId: String
    ): Response<PhotoDetailResponse>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<SearchResponse<PhotoResponse>>

    @GET("collections")
    suspend fun getListCollections(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<List<CollectionResponse>>

    @GET("search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<SearchResponse<CollectionResponse>>

    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") collectionId: Int,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<List<PhotoResponse>>

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        private const val BASE_URL = "https://api.unsplash.com/"

        operator fun invoke(): UnsplashApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter(
                        "client_id",
                        API_KEY
                    )
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .header("Accept-Version", "v1")
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val logInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(logInterceptor)
                .build()

            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(UnsplashApiService::class.java)
        }
    }

}