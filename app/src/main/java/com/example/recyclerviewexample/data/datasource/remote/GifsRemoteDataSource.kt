package com.example.recyclerviewexample.data.datasource.remote

import com.example.recyclerviewexample.data.model.GifDto

interface GifsRemoteDataSource {

    suspend fun getGifs(typeOfGif: String, limit: String): GifDto
}