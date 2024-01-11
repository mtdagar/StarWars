package com.mtdagar.starwars.di

import com.mtdagar.starwars.network.Constants.BASE_URL
import com.mtdagar.starwars.network.StarWarsApi
import com.mtdagar.starwars.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApiService(): StarWarsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(StarWarsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesCharactersRepository(apiService: StarWarsApi): CharactersRepository {
        return CharactersRepository(apiService)
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

}