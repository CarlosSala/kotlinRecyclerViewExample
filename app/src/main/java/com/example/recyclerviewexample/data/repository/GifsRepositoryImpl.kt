package com.example.recyclerviewexample.data.repository

import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSource
import com.example.recyclerviewexample.domain.model.Gif
import javax.inject.Inject

class GifsRepositoryImpl @Inject constructor(
    private val remoteDataSource: GifsRemoteDataSource,
) : GifsRepository {

    override suspend fun getGifs(typeOfGif: String, limit: String): Gif {
        val response = remoteDataSource.getGifs(typeOfGif = typeOfGif, limit = limit)
        return response
    }
}