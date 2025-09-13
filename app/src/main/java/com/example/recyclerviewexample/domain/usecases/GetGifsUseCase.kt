package com.example.recyclerviewexample.domain.usecases

import com.example.recyclerviewexample.data.model.GifDto
import com.example.recyclerviewexample.data.network.GiftApiService

class GetGifsUseCase(
    private val service: GiftApiService
) {

    suspend operator fun invoke(typeOfGif: String, limit: String): GifDto {
        val response = service.getGifs(typeOfGif = typeOfGif, limit = "25")
        return response
    }
}
