package space.rodionov.rickandmorty.domain.repository

import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Single
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.model.Episode
import space.rodionov.rickandmorty.domain.model.Location

interface RamRepository {

     fun getCharacters(): LiveData<List<Character>>
     fun getCharacterById(charId: Int) : LiveData<Character>

     fun getLocations(): LiveData<List<Location>>
     fun getLocationById(locId: Int) : LiveData<Location>

     fun getEpisodes(): LiveData<List<Episode>>
     fun getEpisodeById(epiId: Int) : LiveData<Episode>
}