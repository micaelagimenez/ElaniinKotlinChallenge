package com.project.pokedex.data.models

data class PokemonData(
    var sprites: Sprites,
    var name: String
)

data class Sprites(
    var back_default: String
)