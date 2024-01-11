package com.mtdagar.starwars.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This class converts custom POJO objects to json and vice-versa for Room to use during type
 * conversion.
 */

//todo figure out a way to make only 2 converters for all type to avoid code repetition

class ResultsConverter {

    @TypeConverter
    fun fromFilmsToJson(films: List<String>): String {
        return Gson().toJson(films)
    }

    @TypeConverter
    fun fromJsonToFilms(films: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(films, type)
    }

}