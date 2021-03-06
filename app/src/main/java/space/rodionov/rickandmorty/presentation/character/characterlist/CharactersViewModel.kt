package space.rodionov.rickandmorty.presentation.character.characterlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.dto.toCharacter
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.use_case.GetCharactersUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    var nextPage = state.get<Int>("nextPage") ?: 1
        set(value) {
            field = value
            state.set("nextPage", value)
        }

    private val composite = CompositeDisposable()

    private val fullList = mutableListOf<Character>()
    val characters = MutableLiveData<List<Character>>()

    init {
        onRefresh()
    }

    fun getNewPage() {
        val output = getCharactersUseCase.invoke(nextPage)
        output.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CharactersResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: CharactersResponse) {
                    fullList.addAll(t.results.map {
                        it.toCharacter()
                    })
                    characters.value = fullList
                }

                override fun onError(e: Throwable) {
                    when (e) {
                        is IOException -> { Log.d(TAG, e.localizedMessage
                                    ?: "Server not reached. Check internet connection") }
                        else -> { Log.d(TAG, e.localizedMessage ?: "Unexpected error occurred") }
                    }
                }
            })
    }

    fun onRefresh() {
        fullList.clear()
        characters.value = fullList
        nextPage = 1
        getNewPage()
    }
}





