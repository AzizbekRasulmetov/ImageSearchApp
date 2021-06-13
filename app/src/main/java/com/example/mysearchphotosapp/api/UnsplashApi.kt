package com.example.mysearchphotosapp.api

import com.example.mysearchphotosapp.models.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = "_CQ5zgu-oYhUqD3jGdBN72ZMMNbd0iz_advhxRA31FI" //My API Key
    }

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID $CLIENT_ID"
    )
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}