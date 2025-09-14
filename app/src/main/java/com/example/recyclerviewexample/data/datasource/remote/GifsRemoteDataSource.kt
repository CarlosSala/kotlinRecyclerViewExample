package com.example.recyclerviewexample.data.datasource.remote

import com.example.recyclerviewexample.domain.model.Gif

interface GifsRemoteDataSource {

    suspend fun getGifs(typeOfGif: String, limit: String): Gif
}