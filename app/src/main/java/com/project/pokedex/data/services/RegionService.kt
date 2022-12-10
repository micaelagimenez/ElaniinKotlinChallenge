package com.project.pokedex.data.services

import com.project.pokedex.data.models.PokemonData
import com.project.pokedex.data.models.RegionPokedexData
import com.project.pokedex.data.models.Regions
import com.project.pokedex.data.models.SpecificRegionData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RegionService {
    @GET("/api/v2/region/")
    suspend fun getRegionResponse(): Response<Regions>

    @GET("/api/v2/region/{name}")
    suspend fun getSpecificRegion(@Path("name") name: String): Response<SpecificRegionData>

    @GET("/api/v2/pokedex/{name}")
    suspend fun getRegionPokedex(@Path("name") name: String): Response<RegionPokedexData>

    @GET("/api/v2/pokemon/{name}")
    suspend fun getRegionPokemon(@Path("name") name: String): Response<PokemonData>
}