package com.mtdagar.starwars.network

import com.mtdagar.starwars.data.models.FilmResponse
import com.mtdagar.starwars.data.models.HomeWorldResponse
import com.mtdagar.starwars.data.models.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsApi {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): PeopleResponse

    @GET
    suspend fun getFilm(@Url url: String): FilmResponse

    @GET
    suspend fun getHomeWorld(@Url url: String): HomeWorldResponse
}