package space.rodionov.rickandmorty.presentation.location.locationdetail

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
import space.rodionov.rickandmorty.data.remote.dto.LocationDto
import space.rodionov.rickandmorty.data.remote.dto.toLocation
import space.rodionov.rickandmorty.domain.model.Location
import space.rodionov.rickandmorty.domain.use_case.GetLocationUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

class LocationDetailViewModelFactory @AssistedInject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    @Assisted owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = LocationDetailViewModel(getLocationUseCase, handle) as T
}

@AssistedFactory
interface LocationDetailViewModelAssistedFactory {
    fun create (owner: SavedStateRegistryOwner) : LocationDetailViewModelFactory
}

class LocationDetailViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val  composite = CompositeDisposable()

    private var _location: Location? = null
    val location = MutableLiveData<Location>()

    init {
        state.get<Int>("locId")?.let {
            getLocation(it)
        }
    }

    private fun getLocation(locId: Int) {
        val output = getLocationUseCase.invoke(locId)
        output.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LocationDto> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: LocationDto) {
                    _location = t.toLocation()
                    _location?.let {
                        location.value = it
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
}





