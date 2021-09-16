package space.rodionov.rickandmorty.data.remote.dto

import space.rodionov.rickandmorty.domain.model.Character

data class CharacterDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

fun CharacterDto.toCharacter() = Character(
gender = gender,
    id = id,
    image = image,
    location = location,
    name = name,
    species = species,
    status = status
)