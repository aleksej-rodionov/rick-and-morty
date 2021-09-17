package space.rodionov.rickandmorty.domain.repository

import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Single
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.EpisodesResponse
import space.rodionov.rickandmorty.data.remote.LocationsResponse
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto

interface RamRepository {

     fun getCharactersTest(): CharactersResponse

     fun getCharacters(page: Int): Single<CharactersResponse>
     fun getCharacterById(charId: Int) : Single<CharacterDto>

     fun getLocations(page: Int): Single<LocationsResponse>
     fun getLocationById(locId: Int) : Single<LocationDto>

     fun getEpisodes(page: Int): Single<EpisodesResponse>
     fun getEpisodeById(epiId: Int) : Single<EpisodeDto>
}