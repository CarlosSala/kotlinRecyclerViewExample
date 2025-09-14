package com.example.recyclerviewexample.data.repository

import com.example.recyclerviewexample.domain.model.Gif

interface GifsRepository {

    suspend fun getGifs(typeOfGif: String, limit: String): Gif
}