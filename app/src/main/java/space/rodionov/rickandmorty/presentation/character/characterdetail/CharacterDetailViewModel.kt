package space.rodionov.rickandmorty.presentation.character.characterdetail

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
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.toCharacter
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.use_case.GetCharacterUseCase
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

class CharacterDetailViewModelFactory @AssistedInject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    @Assisted owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = CharacterDetailViewModel(getCharacterUseCase, handle) as T
}

@AssistedFactory
interface CharacterDetailViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner) : CharacterDetailViewModelFactory
}

class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val composite = CompositeDisposable()

    private var _character: Character? = null
    val character = MutableLiveData<Character>()

    init {
        handle.get<Int>("charId")?.let {
            getCharacter(it)
        }
    }

    private fun getCharacter(charId: Int) {
        val output = getCharacterUseCase.invoke(charId)
        output.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CharacterDto> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: CharacterDto) {
                    _character = t.toCharacter()
                    _character?.let {
                        character.value = it
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





