package com.mtdagar.starwars.data.remote.models


import com.google.gson.annotations.SerializedName

data class HomeWorldResponse(
    @SerializedName("name")
    val name: String
)