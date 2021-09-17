package space.rodionov.rickandmorty.data.repository

import io.reactivex.Single
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.EpisodesResponse
import space.rodionov.rickandmorty.data.remote.LocationsResponse
import space.rodionov.rickandmorty.data.remote.RickAndMortyApi
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto
import space.rodionov.rickandmorty.domain.repository.RamRepository
import javax.inject.Inject


class RamRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
) : RamRepository {
    override fun getCharactersTest(): CharactersResponse {
        return api.getCharactersTest()
    }

    override fun getCharacters(page: Int): Single<CharactersResponse> {
        return api.getCharacters(page)
    }

    override fun getCharacterById(charId: Int): Single<CharacterDto> {
        return api.getCharacterById(charId)
    }

    override fun getLocations(page: Int): Single<LocationsResponse> {
        return api.getLocations(page)
    }

    override fun getLocationById(locId: Int): Single<LocationDto> {
        return api.getLocationById(locId)
    }

    override fun getEpisodes(page: Int): Single<EpisodesResponse> {
        return api.getEpisodes(page)
    }

    override fun getEpisodeById(epiId: Int): Single<EpisodeDto> {
        return api.getEpisodeById(epiId)
    }
}





