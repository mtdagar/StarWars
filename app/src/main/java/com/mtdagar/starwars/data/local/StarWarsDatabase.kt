package com.mtdagar.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.data.remote.models.CharacterResponse

@Database(
    entities = [
        CharacterEntity::class,
        CharacterRemoteKeys::class
    ],
    version = 1
)
@TypeConverters(ResultsConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao

    companion object {
        const val DATABASE_NAME = "star_wars_db"
    }

}