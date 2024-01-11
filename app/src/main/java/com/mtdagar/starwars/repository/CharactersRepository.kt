package com.mtdagar.starwars.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mtdagar.starwars.network.Constants.NETWORK_PAGE_SIZE
import com.mtdagar.starwars.network.SafeApiCall
import com.mtdagar.starwars.network.StarWarsApi
import com.mtdagar.starwars.data.models.Character
import com.mtdagar.starwars.repository.paging.CharactersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: StarWarsApi) : SafeApiCall() {

    fun getCharacters(searchString: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(apiService, searchString)
            }
        ).flow
    }

    suspend fun getFilm(url: String) = safeApiCall {
        apiService.getFilm(url)
    }

    suspend fun getHomeWorld(url: String) = safeApiCall {
        apiService.getHomeWorld(url)
    }
}