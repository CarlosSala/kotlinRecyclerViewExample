package com.example.recyclerviewexample.data.datasource.remote

import com.example.recyclerviewexample.data.mapper.toDomain
import com.example.recyclerviewexample.data.network.GiftApiService
import com.example.recyclerviewexample.domain.model.Gif

class GifsRemoteDataSourceImpl(
    private val giftApiService: GiftApiService
) : GifsRemoteDataSource {

    override suspend fun getGifs(typeOfGif: String, limit: String): Gif {
        val response = giftApiService.getGifs(typeOfGif = typeOfGif, limit = limit)
        return response.toDomain()
    }
}