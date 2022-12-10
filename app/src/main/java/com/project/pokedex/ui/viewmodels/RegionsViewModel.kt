package com.project.pokedex.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.pokedex.data.datasources.Resource
import com.project.pokedex.data.models.PokemonData
import com.project.pokedex.data.models.RegionPokedexData
import com.project.pokedex.data.models.Regions
import com.project.pokedex.data.models.SpecificRegionData
import com.project.pokedex.data.repositories.RegionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegionsViewModel @Inject constructor(
    private val regionsRepository: RegionsRepository
) : ViewModel() {

    private val _regions = MutableLiveData<Resource<Regions>>()
    val regions: LiveData<Resource<Regions>> get() = _regions
    private val _regionData =  MutableLiveData<Resource<SpecificRegionData>>()
    val regionData: LiveData<Resource<SpecificRegionData>> get() = _regionData
    private val _regionPokedex =  MutableLiveData<Resource<RegionPokedexData>>()
    val regionPokedex: LiveData<Resource<RegionPokedexData>> get() = _regionPokedex
    private val _regionPokemon =  MutableLiveData<Resource<PokemonData>>()
    val regionPokemon: LiveData<Resource<PokemonData>> get() = _regionPokemon

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.message?.let { Resource.error(it, exception) }
    }

    fun getRegions() = viewModelScope.launch(Dispatchers.Main + handler) {
        _regions.value = Resource.loading()
        val response = withContext(Dispatchers.IO) {
            regionsRepository.getRegions()
        }
        _regions.value = response
    }

    fun navigateToRegion(name:String)  = viewModelScope.launch(Dispatchers.Main + handler) {
        _regionData.value = Resource.loading()
        val response = withContext(Dispatchers.IO) {
            regionsRepository.getSpecificRegionData(name)
        }
        _regionData.value = response
    }

    fun getRegionPokedexData(name:String)  = viewModelScope.launch(Dispatchers.Main + handler) {
        _regionPokedex.value = Resource.loading()
        val response = withContext(Dispatchers.IO) {
            regionsRepository.getRegionPokedex(name)
        }
        _regionPokedex.value = response
    }

    fun getRegionPokemonData(name:String)  = viewModelScope.launch(Dispatchers.Main + handler) {
        _regionPokemon.value = Resource.loading()
        val response = withContext(Dispatchers.IO) {
            regionsRepository.getRegionPokemon(name)
        }
        _regionPokemon.value = response
    }
}