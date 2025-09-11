package com.example.recyclerviewexample.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstanceRetrofit(): GiftApiService {
        return retrofit.create(GiftApiService::class.java)
    }
}
