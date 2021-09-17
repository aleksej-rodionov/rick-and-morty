package space.rodionov.rickandmorty.data.remote.dto

import com.google.gson.annotations.SerializedName
import space.rodionov.rickandmorty.domain.model.Episode

data class EpisodeDto(
    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeDto.toEpisode() = Episode(
    airDate = airDate,
    episode = episode,
    id = id,
    name = name
)