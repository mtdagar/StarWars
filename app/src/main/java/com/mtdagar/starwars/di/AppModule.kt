package com.mtdagar.starwars.di

import android.app.Application
import androidx.room.Room
import com.mtdagar.starwars.data.local.StarWarsDatabase
import com.mtdagar.starwars.network.Constants.BASE_URL
import com.mtdagar.starwars.network.StarWarsApi
import com.mtdagar.starwars.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesStarWarDatabase(application: Application): StarWarsDatabase {
        return Room.databaseBuilder(
            application,
            StarWarsDatabase::class.java,
            StarWarsDatabase.DATABASE_NAME
        ).build()
    }

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
    fun providesCharactersRepository(
        apiService: StarWarsApi,
        starWarsDatabase: StarWarsDatabase
    ): CharacterRepository {
        return CharacterRepository(apiService, starWarsDatabase)
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

}