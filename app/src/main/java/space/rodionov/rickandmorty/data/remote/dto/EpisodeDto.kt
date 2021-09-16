package space.rodionov.rickandmorty.data.remote.dto

import space.rodionov.rickandmorty.domain.model.Episode

data class EpisodeDto(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeDto.toEpisode() = Episode(
    air_date = air_date,
    episode = episode,
    id = id,
    name = name
)