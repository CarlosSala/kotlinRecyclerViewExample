package com.example.recyclerviewexample.data.repository

import com.example.recyclerviewexample.data.model.GifDto

interface GifsRepository {

    suspend fun getGifs(typeOfGif: String, limit: String): GifDto
}