package space.rodionov.rickandmorty.presentation.character.characterlist

import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.dto.toCharacter
import space.rodionov.rickandmorty.data.storage.SharedPrefStorage
import space.rodionov.rickandmorty.data.storage.SharedPrefStorageImpl
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.use_case.GetCharactersUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "VM LOGS"

class CharactersViewModelFactory @AssistedInject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val storageImpl: SharedPrefStorageImpl,
    @Assisted owner: SavedStateRegistryOwner,
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = CharactersViewModel(getCharactersUseCase, storageImpl, handle) as T
}

@AssistedFactory
interface CharactersViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner/*, storage: SharedPrefStorage*/): CharactersViewModelFactory
}

class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val storage: SharedPrefStorage,
    private val handle: SavedStateHandle
) : ViewModel() {
    var nextPage = handle.get<Int>("nextPage") ?: 1
        set(value) {
            field = value
            handle.set("nextPage", value)
        }

    private var _isNight = handle.getLiveData("isNight", false)
    val isNight: LiveData<Boolean> = _isNight

    private val composite = CompositeDisposable()

    private val fullList = mutableListOf<Character>()
    val characters = MutableLiveData<List<Character>>()

    init {
        onRefresh()
    }

    fun saveMode(night: Boolean?) {
//        val nm = isNight.value?.let { !it }
        storage.setBoolean("isNightMode", night ?: false)
//        delay(500)
//        getMode()
    }

    fun getMode(): Boolean {
        val night =  storage.getBoolean("isNightMode")
        return night
    }

    fun setSSHMode(night: Boolean) {
        _isNight.value = night
    }

    fun onRefresh(/*owner: LifecycleOwner*/) {
        fullList.clear()
        characters.value = fullList
        nextPage = 1
        getNewPage(/*owner*/)
    }

//    fun getNewPage(owner: LifecycleOwner) {
//        getCharactersUseCase.invoke(nextPage).observe(owner) {
//            fullList.addAll(it)
//            characters.value = fullList
//        }
//    }

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





