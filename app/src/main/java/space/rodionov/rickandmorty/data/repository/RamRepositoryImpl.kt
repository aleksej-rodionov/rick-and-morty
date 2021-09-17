package space.rodionov.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.EpisodesResponse
import space.rodionov.rickandmorty.data.remote.LocationsResponse
import space.rodionov.rickandmorty.data.remote.RickAndMortyApi
import space.rodionov.rickandmorty.data.remote.dto.*
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.model.Episode
import space.rodionov.rickandmorty.domain.model.Location
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





