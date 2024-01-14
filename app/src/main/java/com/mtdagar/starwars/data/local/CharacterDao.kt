package com.mtdagar.starwars.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.data.remote.models.CharacterResponse

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getAll(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()

    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllSortedByName(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY birthYear ASC")
    fun getAllSortedByBirth(): PagingSource<Int, CharacterEntity>
}