package com.project.pokedex.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SpecificRegionData(
    var id: Int,
    var locations: List<Locations>,
    var main_generation: MainGeneration,
    var name: String,
    var names: List<Names>,
    var pokedexes: List<Pokedexes>
)

data class Locations(
    var name: String,
    var url: String
)

data class MainGeneration(
    var name: String,
    var url: String
)

data class Names(
    var language: Language,
    var name: String
)

data class Language(
    var name: String,
    var url: String
)

@Parcelize
data class Pokedexes(
    var name: String? = "",
    var url: String? = ""
) : Parcelable