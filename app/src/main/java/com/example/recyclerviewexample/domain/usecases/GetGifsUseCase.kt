package com.example.recyclerviewexample.domain.usecases

import com.example.recyclerviewexample.data.repository.GifsRepository
import com.example.recyclerviewexample.domain.model.Gif

class GetGifsUseCase(
    private val gifsRepository: GifsRepository,
) {

    suspend operator fun invoke(typeOfGif: String, limit: String): Gif {
        val response = gifsRepository.getGifs(typeOfGif = typeOfGif, limit = "25")
        return response
    }
}
