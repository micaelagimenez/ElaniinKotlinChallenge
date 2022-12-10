package com.project.pokedex.data.repositories

import com.project.pokedex.data.datasources.RegionDataSource
import javax.inject.Inject

class RegionsRepository @Inject constructor(
    private val remote: RegionDataSource
) {
    suspend fun getRegions() =
        remote.getRegions()

    suspend fun getSpecificRegionData(name:String) =
        remote.getSpecificRegionData(name)

    suspend fun getRegionPokedex(name:String) =
        remote.getRegionPokedex(name)

    suspend fun getRegionPokemon(name:String) =
        remote.getRegionPokemon(name)
}
