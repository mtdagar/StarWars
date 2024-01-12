package com.mtdagar.starwars.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mtdagar.starwars.data.local.StarWarsDatabase
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.network.Constants.NETWORK_PAGE_SIZE
import com.mtdagar.starwars.network.SafeApiCall
import com.mtdagar.starwars.network.StarWarsApi
import com.mtdagar.starwars.data.remote.models.CharacterResponse
import com.mtdagar.starwars.repository.paging.CharacterRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: StarWarsApi,
    private val starWarsDatabase: StarWarsDatabase
) : SafeApiCall() {

    private val pagingSourceFactory = { starWarsDatabase.characterDao().getAll() }

    @OptIn(ExperimentalPagingApi::class)
    fun getCharacters(searchString: String): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                starWarsDatabase,
                apiService
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getFilm(url: String) = safeApiCall {
        apiService.getFilm(url)
    }

    suspend fun getHomeWorld(url: String) = safeApiCall {
        apiService.getHomeWorld(url)
    }
}