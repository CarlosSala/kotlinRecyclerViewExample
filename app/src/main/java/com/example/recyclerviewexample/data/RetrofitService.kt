package com.example.recyclerviewexample.data

import com.example.recyclerviewexample.BuildConfig.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY: String = API_KEY_SAFE
const val BASE_URL: String = "https://api.giphy.com/v1/gifs/"

interface RetrofitService {

    @GET(
        "search?api_key=${API_KEY}&offset=0&rating=g&lang=es&bundle=low_bandwidth"
    )
    suspend fun getGifs(@Query("q") typeOfGif: String, @Query("limit") limit: String): GifDataResponse


    object RetrofitServiceFactory {
        fun makeRetrofitService(): RetrofitService {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService::class.java)
        }
    }
}