package space.rodionov.rickandmorty.data.remote

import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto

data class EpisodesResponse(
    val results: List<EpisodeDto>
)