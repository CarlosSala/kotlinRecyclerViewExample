package com.example.recyclerviewexample.di

import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSource
import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSourceImpl
import com.example.recyclerviewexample.data.network.BASE_URL
import com.example.recyclerviewexample.data.network.GiftApiService
import com.example.recyclerviewexample.data.repository.GifsRepository
import com.example.recyclerviewexample.data.repository.GifsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): GiftApiService {
        return retrofit.create(GiftApiService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(apiService: GiftApiService): GifsRemoteDataSource {
        return GifsRemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideRepository(gifsRemoteDataSource: GifsRemoteDataSource): GifsRepository {
        return GifsRepositoryImpl(gifsRemoteDataSource)
    }
}