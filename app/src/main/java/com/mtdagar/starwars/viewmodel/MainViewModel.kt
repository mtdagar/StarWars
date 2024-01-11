package com.mtdagar.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mtdagar.starwars.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.mtdagar.starwars.data.models.Character

@HiltViewModel
class MainViewModel @Inject constructor(private val charactersRepository: CharactersRepository) :
    ViewModel() {

    fun getCharacters(searchString: String): Flow<PagingData<Character>> {
        return charactersRepository.getCharacters(searchString).cachedIn(viewModelScope)
    }
}