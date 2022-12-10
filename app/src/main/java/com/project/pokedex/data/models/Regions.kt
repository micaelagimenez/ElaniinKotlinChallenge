package com.project.pokedex.data.models

data class Regions(
    var count: Int,
    var next: String,
    var previous: String,
    var results: List<Region>
)

data class Region(
    var name: String,
    var url: String
)