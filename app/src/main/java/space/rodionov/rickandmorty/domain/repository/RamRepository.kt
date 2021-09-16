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
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.model.Episode
import space.rodionov.rickandmorty.domain.model.Location

interface RamRepository {

     fun getCharactersTest(): CharactersResponse

     fun getCharacters(): Single<CharactersResponse>
     fun getCharacterById(charId: Int) : Single<CharacterDto>

     fun getLocations(): Single<LocationsResponse>
     fun getLocationById(locId: Int) : Single<LocationDto>

     fun getEpisodes(): Single<EpisodesResponse>
     fun getEpisodeById(epiId: Int) : Single<EpisodeDto>
}