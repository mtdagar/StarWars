package com.mtdagar.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.data.local.models.SortingOptions
import com.mtdagar.starwars.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.mtdagar.starwars.data.remote.models.CharacterResponse

@HiltViewModel
class MainViewModel @Inject constructor(private val charactersRepository: CharacterRepository) :
    ViewModel() {

    fun getCharacters(searchString: String, sortingOptions: SortingOptions?): Flow<PagingData<CharacterEntity>> {
        return charactersRepository.getCharacters(searchString, sortingOptions).cachedIn(viewModelScope)
    }
}