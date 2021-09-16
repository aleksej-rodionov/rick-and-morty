package space.rodionov.rickandmorty.domain.model

import space.rodionov.rickandmorty.data.remote.dto.Location

data class Character(
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val species: String,
    val status: String
)
