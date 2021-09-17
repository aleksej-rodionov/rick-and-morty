package space.rodionov.rickandmorty.presentation.location.locationlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import space.rodionov.rickandmorty.data.remote.LocationsResponse
import space.rodionov.rickandmorty.data.remote.dto.toLocation
import space.rodionov.rickandmorty.domain.model.Location
import space.rodionov.rickandmorty.domain.use_case.GetLocationsUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    var nextPage = state.get<Int>("nextPage") ?: 1
        set(value) {
            field = value
            state.set("nextPage", value)
        }

    private val composite = CompositeDisposable()

    private val fullList = mutableListOf<Location>()
    val locations = MutableLiveData<List<Location>>()

    init {
        onRefresh()
    }

    fun getNewPage() {
        val output = getLocationsUseCase.invoke(nextPage)
        output.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LocationsResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: LocationsResponse) {
                    fullList.addAll(t.results.map {
                        it.toLocation()
                    })
                    locations.value = fullList
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
        fullList.clear()
        locations.value = fullList
        nextPage = 1
        getNewPage()
    }
}