package com.mtdagar.starwars.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.data.local.models.mapFromListModel
import com.mtdagar.starwars.network.Constants.FIRST_PAGE_INDEX
import com.mtdagar.starwars.network.StarWarsApi
import com.mtdagar.starwars.data.remote.models.CharacterResponse
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(private val apiService: StarWarsApi, private val searchString: String) :
    PagingSource<Int, CharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val position = params.key ?: FIRST_PAGE_INDEX
        return try {

            val response = apiService.getCharacters(position)

            val characters = response.results.mapFromListModel()

            val filteredData = characters.filter { it.name?.contains(searchString, true) ?: false }

            val nextKey = position + 1
            val prevKey = if (position == 1) null else position - 1

            LoadResult.Page(
                data = filteredData,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition
    }
}