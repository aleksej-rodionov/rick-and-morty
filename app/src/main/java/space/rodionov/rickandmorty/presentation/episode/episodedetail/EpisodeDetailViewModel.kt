package space.rodionov.rickandmorty.presentation.episode.episodedetail

import android.os.Bundle
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
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.toEpisode
import space.rodionov.rickandmorty.domain.model.Episode
import space.rodionov.rickandmorty.domain.use_case.GetEpisodeUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

class EpisodeDetailViewModelFactory @AssistedInject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return EpisodeDetailViewModel(getEpisodeUseCase, handle) as T
    }
}

@AssistedFactory
interface EpisodeDetailViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle?) : EpisodeDetailViewModelFactory
}

class EpisodeDetailViewModel @Inject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    private val composite = CompositeDisposable()

    private var _episode: Episode? = null
    val episode = MutableLiveData<Episode>()

    init {
        state.get<Int>("epiId")?.let {
            getEpisode(it)
        }
    }

    private fun getEpisode(epiId: Int) {
        val output = getEpisodeUseCase.invoke(epiId)
        output.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<EpisodeDto> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: EpisodeDto) {
                    _episode = t.toEpisode()
                    _episode?.let {
                        episode.value = it
                    }
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

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}





