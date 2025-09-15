package com.example.recyclerviewexample.data.util

import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSource
import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSourceImpl
import com.example.recyclerviewexample.data.network.GiftApiService
import com.example.recyclerviewexample.data.network.RetrofitHelper
import com.example.recyclerviewexample.data.repository.GifsRepository
import com.example.recyclerviewexample.data.repository.GifsRepositoryImpl
import com.example.recyclerviewexample.domain.usecases.GetGifsUseCase

object DataComponents {

    // this property will be initialized when it's first accessed
    private val giftApiService: GiftApiService by lazy {
        RetrofitHelper.getInstanceRetrofit()
    }

    val gifsRemoteDataSource: GifsRemoteDataSource by lazy {
        GifsRemoteDataSourceImpl(giftApiService)
    }

    val gifsRepository: GifsRepository by lazy {
        GifsRepositoryImpl(remoteDataSource = gifsRemoteDataSource)
    }

    val gifsUseCase: GetGifsUseCase by lazy {
        GetGifsUseCase(gifsRepository)
    }
}