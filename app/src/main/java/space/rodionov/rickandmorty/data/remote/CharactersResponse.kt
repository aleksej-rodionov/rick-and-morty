package space.rodionov.rickandmorty.data.remote

import space.rodionov.rickandmorty.data.remote.dto.CharacterDto

data class CharactersResponse(
    val results: List<CharacterDto>
)
