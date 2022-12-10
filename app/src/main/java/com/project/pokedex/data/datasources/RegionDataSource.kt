package com.project.pokedex.data.datasources

import com.project.pokedex.data.services.RegionService
import javax.inject.Inject

class RegionDataSource @Inject constructor(private val regionService: RegionService) : BaseDataSource() {

    suspend fun getRegions() = getResult { regionService.getRegionResponse() }
    suspend fun getSpecificRegionData(name:String) = getResult { regionService.getSpecificRegion(name) }
    suspend fun getRegionPokedex(name:String) = getResult { regionService.getRegionPokedex(name) }
    suspend fun getRegionPokemon(name:String) = getResult { regionService.getRegionPokemon(name) }
}