package com.project.pokedex.data.models

data class Team (
    var name: String? = null,
    var number: String? = null,
    var type: String? = null,
    var pokedexDescription: String? = null,
    var pokemons: List<PokemonSaved> = listOf()
    )

data class PokemonSaved(
    var name: String = "",
    var image: String = ""
)