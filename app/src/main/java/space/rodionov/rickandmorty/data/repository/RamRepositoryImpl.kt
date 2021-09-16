package space.rodionov.rickandmorty.data.repository

import space.rodionov.rickandmorty.data.remote.RickAndMortyApi
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto
import space.rodionov.rickandmorty.domain.repository.RamRepository
import javax.inject.Inject


class RamRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
) : RamRepository {

    override suspend fun getCharacters() = api.getCharacters().results

    override suspend fun getCharacterById(charId: Int) = api.getCharacterById(charId)

    override suspend fun getLocations() = api.getLocations().results

    override suspend fun getLocationById(locId: Int) = api.getLocationById(locId)

    override suspend fun getEpisodes() = api.getEpisodes().results

    override suspend fun getEpisodeById(epiId: Int) = api.getEpisodeById(epiId)
}





