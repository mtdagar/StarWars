package com.mtdagar.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_remote_keys")
data class CharacterRemoteKeys(
    @PrimaryKey val characterId: String,
    val prevKey: Int?,
    val nextKey: Int?
)