package com.mtdagar.starwars.data.local.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "characters")
data class CharacterEntity (
    @PrimaryKey
    var id: String = "",
    @SerializedName("birth_year")
    val birthYear: String?,
    @SerializedName("eye_color")
    val eyeColor: String?,
    val films: List<String>,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("hair_color")
    val hairColor: String?,
    @SerializedName("height")
    val height: String?,
    val homeworld: String,
    val mass: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("skin_color")
    val skinColor: String?
) : Parcelable