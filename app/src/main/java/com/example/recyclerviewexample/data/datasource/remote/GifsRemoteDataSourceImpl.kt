package com.example.recyclerviewexample.data.datasource.remote

import com.example.recyclerviewexample.data.model.GifDto
import com.example.recyclerviewexample.data.network.GiftApiService

class GifsRemoteDataSourceImpl(
    private val giftApiService: GiftApiService
) : GifsRemoteDataSource {

    override suspend fun getGifs(typeOfGif: String, limit: String): GifDto {
        val response = giftApiService.getGifs(typeOfGif = typeOfGif, limit = limit)
        return response
    }
}