package com.example.recyclerviewexample.data.repository

import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSource
import com.example.recyclerviewexample.data.model.GifDto

class GifsRepositoryImpl(
    private val remoteDataSource: GifsRemoteDataSource
) : GifsRepository {

    override suspend fun getGifs(typeOfGif: String, limit: String): GifDto {
        val response = remoteDataSource.getGifs(typeOfGif = typeOfGif, limit = limit)
        return response
    }
}