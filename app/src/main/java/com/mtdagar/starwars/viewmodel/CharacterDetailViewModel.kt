package com.mtdagar.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtdagar.starwars.data.Resource
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.data.remote.models.FilmResponse
import com.mtdagar.starwars.data.remote.models.HomeWorldResponse
import com.mtdagar.starwars.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val myArguments = savedStateHandle.get<CharacterEntity>("characterDetails")

    private val _details = MutableLiveData<CharacterEntity>()
    val details: LiveData<CharacterEntity>
        get() = _details

    private val _homeWorld = MutableStateFlow<Resource<HomeWorldResponse>>(Resource.Empty())
    val homeWorldResponse: StateFlow<Resource<HomeWorldResponse>>
        get() = _homeWorld

    private val _filmDetails = MutableStateFlow<Resource<List<FilmResponse>>>(Resource.Empty())
    val filmResponseDetails: StateFlow<Resource<List<FilmResponse>>>
        get() = _filmDetails

    private val filmsList: ArrayList<FilmResponse> = ArrayList()

    init {
        _details.value = myArguments!!
        getHomeWorldData(myArguments.homeworld)
        getFilmData()
    }

    private fun getFilmData() {
        myArguments!!.films.forEach { film ->

            viewModelScope.launch(Dispatchers.IO) {
                _filmDetails.value = Resource.Loading()
                when (val characterDetailsResponse = characterRepository.getFilm(film)) {
                    is Resource.Failure -> {
                        _filmDetails.value =
                            Resource.Failure(characterDetailsResponse.message!!)
                    }
                    is Resource.Success -> {
                        if (characterDetailsResponse.data == null) {
                            _filmDetails.value = Resource.Failure("Empty Film Response List")
                        } else {
                            filmsList.add(characterDetailsResponse.data)
                            _filmDetails.value = Resource.Success(filmsList)
                        }
                    }

                    else -> {}
                }
            }
        }
    }


    private fun getHomeWorldData(homeWorldUrl: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _filmDetails.value = Resource.Loading()
            when (val homeWorldResponse = characterRepository.getHomeWorld(homeWorldUrl)) {
                is Resource.Failure -> {
                    _homeWorld.value = Resource.Failure(homeWorldResponse.message!!)
                }
                is Resource.Success -> {
                    if (homeWorldResponse.data == null) {
                        _homeWorld.value = Resource.Failure("N/A")
                    } else {
                        _homeWorld.value = Resource.Success(homeWorldResponse.data)
                    }
                }

                else -> {}
            }
        }
    }

}