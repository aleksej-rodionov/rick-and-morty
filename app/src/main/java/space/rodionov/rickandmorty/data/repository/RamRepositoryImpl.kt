package space.rodionov.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
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

    private val composite = CompositeDisposable()

    override fun getCharacters(): LiveData<List<Character>> {
        val characters = MutableLiveData<List<Character>>()
        api.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CharactersResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: CharactersResponse) {
                    characters.value = t.results.map {
                        it.toCharacter()
                    }
                }

                override fun onError(e: Throwable) {
                    //todo emit error
                }
            })
        return characters
    }

    override fun getCharacterById(charId: Int): LiveData<Character> {
        val character = MutableLiveData<Character>()
        api.getCharacterById(charId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CharacterDto> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: CharacterDto) {
                    character.value = t.toCharacter()
                }

                override fun onError(e: Throwable) {
                    // todo emit error
                }
            })
        return character
    }

    override fun getLocations(): LiveData<List<Location>> {
        val locations = MutableLiveData<List<Location>>()
        api.getLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LocationsResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: LocationsResponse) {
                    locations.value = t.results.map {
                        it.toLocation()
                    }
                }

                override fun onError(e: Throwable) {
                    //todo emit error
                }
            })
        return locations
    }

    override fun getLocationById(locId: Int): LiveData<Location> {
        val location = MutableLiveData<Location>()
        api.getLocationById(locId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LocationDto> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: LocationDto) {
                    location.value = t.toLocation()
                }

                override fun onError(e: Throwable) {
                    // todo emit error
                }
            })
        return location
    }

    override fun getEpisodes(): LiveData<List<Episode>> {
        val episodes = MutableLiveData<List<Episode>>()
        api.getEpisodes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<EpisodesResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: EpisodesResponse) {
                    episodes.value = t.results.map {
                        it.toEpisode()
                    }
                }

                override fun onError(e: Throwable) {
                    //todo emit error
                }
            })
        return episodes
    }

    override fun getEpisodeById(epiId: Int): LiveData<Episode> {
        val episode = MutableLiveData<Episode>()
        api.getEpisodeById(epiId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<EpisodeDto> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: EpisodeDto) {
                    episode.value = t.toEpisode()
                }

                override fun onError(e: Throwable) {
                    // todo emit error
                }
            })
        return episode
    }
}





