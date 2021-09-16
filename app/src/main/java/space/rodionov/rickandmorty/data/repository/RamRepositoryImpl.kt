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

    //    private val composite = CompositeDisposable()

    override fun getCharacters(): Single<CharactersResponse> {
        return api.getCharacters()
        /* val characters = MutableLiveData<List<Character>>()
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
         return characters*/
    }

    override fun getCharacterById(charId: Int): Single<CharacterDto> {
        return api.getCharacterById(charId)
        /* val character = MutableLiveData<Character>()
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
         return character*/
    }

    override fun getLocations(): Single<LocationsResponse> {
        return api.getLocations()
        /*     val locations = MutableLiveData<List<Location>>()
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
             return locations*/
    }

    override fun getLocationById(locId: Int): Single<LocationDto> {
        return api.getLocationById(locId)
        /*   val location = MutableLiveData<Location>()
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
           return location*/
    }

    override fun getEpisodes(): Single<EpisodesResponse> {
        return api.getEpisodes()
        /*  val episodes = MutableLiveData<List<Episode>>()
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
            return episodes*/
    }

    override fun getEpisodeById(epiId: Int): Single<EpisodeDto> {
        return api.getEpisodeById(epiId)
        /*    val episode = MutableLiveData<Episode>()
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
            return episode*/
    }
}





