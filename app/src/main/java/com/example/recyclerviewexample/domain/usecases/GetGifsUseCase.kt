package com.example.recyclerviewexample.domain.usecases

import com.example.recyclerviewexample.data.repository.GifsRepositoryImpl
import com.example.recyclerviewexample.domain.model.Gif

class GetGifsUseCase(
    private val gifsRepositoryImpl: GifsRepositoryImpl
) {

    suspend operator fun invoke(typeOfGif: String, limit: String): Gif {
        val response = gifsRepositoryImpl.getGifs(typeOfGif = typeOfGif, limit = "25")
        return response
    }
}
