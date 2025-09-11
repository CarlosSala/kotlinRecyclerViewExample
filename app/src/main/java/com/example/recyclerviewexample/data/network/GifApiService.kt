package com.example.recyclerviewexample.data.network

import com.example.recyclerviewexample.BuildConfig.*
import com.example.recyclerviewexample.data.model.GifDto
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY: String = API_KEY_SAFE
const val BASE_URL: String = "https://api.giphy.com/v1/gifs/"

interface GiftApiService {

    @GET("search?api_key=${API_KEY}&offset=0&rating=g&lang=es&bundle=low_bandwidth")
    suspend fun getGifs(
        @Query("q") typeOfGif: String,
        @Query("limit") limit: String
    ): GifDto
}