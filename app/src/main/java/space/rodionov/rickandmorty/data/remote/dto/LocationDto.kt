package space.rodionov.rickandmorty.data.remote.dto

import space.rodionov.rickandmorty.domain.model.Location

data class LocationDto(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

fun LocationDto.toLocation() = Location(
    dimension = dimension,
    id = id,
    name = name,
    type = type
)