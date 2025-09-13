package com.example.recyclerviewexample.domain.usecases

import com.example.recyclerviewexample.data.model.GifDto
import com.example.recyclerviewexample.data.repository.GifsRepositoryImpl

class GetGifsUseCase(
    private val gifsRepositoryImpl: GifsRepositoryImpl
) {

    suspend operator fun invoke(typeOfGif: String, limit: String): GifDto {
        val response = gifsRepositoryImpl.getGifs(typeOfGif = typeOfGif, limit = "25")
        return response
    }
}
