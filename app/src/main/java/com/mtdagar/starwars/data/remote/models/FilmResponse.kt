package com.mtdagar.starwars.data.remote.models


import com.google.gson.annotations.SerializedName

data class FilmResponse(
    @SerializedName("opening_crawl")
    val openingCrawl: String,
    @SerializedName("title")
    val title: String
)