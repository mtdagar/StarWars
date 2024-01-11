package com.mtdagar.starwars.data.local.models

import com.mtdagar.starwars.data.remote.models.CharacterResponse
import java.util.UUID

fun CharacterResponse.mapFromEntity() = CharacterEntity(
    id = UUID.randomUUID().toString(),
    birthYear = this.birthYear,
    eyeColor = this.eyeColor,
    gender = this.gender,
    hairColor = this.hairColor,
    height = this.height,
    name = this.name,
    skinColor = this.skinColor
)

fun List<CharacterResponse>.mapFromListModel(): List<CharacterEntity>{
    return this.map{
        it.mapFromEntity()
    }
}