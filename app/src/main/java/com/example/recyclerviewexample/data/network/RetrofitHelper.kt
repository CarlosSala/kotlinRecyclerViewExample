package com.example.recyclerviewexample.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * The Retrofit interface (e.g., `GiftApiService::class.java`) defines a CONTRACT
     * for network operations. Since interfaces cannot be instantiated directly,
     * Retrofit uses the "Dynamic Proxy" pattern (or code generation via KSP)
     * to provide a functional IMPLEMENTATION of this interface.
     * This generated class contains all the necessary logic to perform network calls
     * and handle responses, based on the interface's annotations.
     */
    fun getInstanceRetrofit(): GiftApiService {
        return retrofit.create(GiftApiService::class.java)
    }
}
