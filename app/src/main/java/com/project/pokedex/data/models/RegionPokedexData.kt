package com.project.pokedex.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RegionPokedexData(
    var descriptions: List<Description>,
    var id: Int,
    var is_main_series: Boolean,
    var name: String,
    var names: List<Names>,
    var pokemon_entries: List<PokemonEntries>,
    var regions: Region,
    var version_group: List<VersionGroup>
)

data class Description(
    var description: String,
    var language: Language
)

data class PokemonEntries(
    var entry_number: Int,
    var pokemon_species: PokemonSpecies
)

@Parcelize
data class PokemonSpecies(
    var name: String= "",
    var url: String = ""
): Parcelable

data class VersionGroup(
    var name: String,
    var url: String
)