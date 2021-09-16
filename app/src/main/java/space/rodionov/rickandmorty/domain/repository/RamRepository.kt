package space.rodionov.rickandmorty.domain.repository

import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto

interface RamRepository {

    suspend fun getCharacters(): List<CharacterDto>
    suspend fun getCharacterById(charId: Int) : CharacterDto

    suspend fun getLocations(): List<LocationDto>
    suspend fun getLocationById(locId: Int) : LocationDto

    suspend fun getEpisodes(): List<EpisodeDto>
    suspend fun getEpisodeById(epiId: Int) : EpisodeDto
}