package space.rodionov.rickandmorty.presentation.episode.episodelist

import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import space.rodionov.rickandmorty.data.remote.EpisodesResponse
import space.rodionov.rickandmorty.data.remote.dto.toEpisode
import space.rodionov.rickandmorty.domain.model.Episode
import space.rodionov.rickandmorty.domain.use_case.GetEpisodesUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

class EpisodesViewModelFactory @AssistedInject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    @Assisted owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return EpisodesViewModel(getEpisodesUseCase, handle) as T
    }
}

@AssistedFactory
interface EpisodesViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner): EpisodesViewModelFactory
}

class EpisodesViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val handle: SavedStateHandle
): ViewModel() {
    var nextPage = handle.get<Int>("nextPage") ?: 1
        set(value) {
            field = value
            handle.set("nextPage", value)
        }

    private val composite = CompositeDisposable()

    private var _episodes = mutableListOf<Episode>()
    val episodes = MutableLiveData<List<Episode>>()

    init {
        onRefresh()
    }

    fun getNewPage() {
        val output = getEpisodesUseCase.invoke(nextPage)
        output.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<EpisodesResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: EpisodesResponse) {
                    _episodes.addAll(t.results.map {
                        it.toEpisode()
                    })
                    episodes.value = _episodes
                }

                override fun onError(e: Throwable) {
                    when (e) {
                        is IOException -> {
                            Log.d(
                                TAG, e.localizedMessage
                                    ?: "Server not reached. Check internet connection"
                            )
                        }
                        else -> {
                            Log.d(TAG, e.localizedMessage ?: "Unexpected error occurred")
                        }
                    }
                }
            })

    }

    fun onRefresh() {
        _episodes.clear()
        episodes.value = _episodes
        nextPage = 1
        getNewPage()
    }
}





