package space.rodionov.rickandmorty.domain.use_case

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import space.rodionov.rickandmorty.common.Resource
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.dto.toCharacter
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.repository.RamRepository
import java.io.IOException
import javax.inject.Inject

private const val TAG = "CharUseCase LOGS"

class GetCharactersUseCase @Inject constructor(
    private val repository: RamRepository
) {

    private val composite = CompositeDisposable()

    operator fun invoke(): LiveData/*<Resource*/<List<Character>>/*>*/ {
        val characters = MutableLiveData/*<Resource*/<List<Character>>/*>*/()
        repository.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CharactersResponse> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onSuccess(t: CharactersResponse) {
                    characters.value = /*Resource.Success(*/t.results.map {
                        it.toCharacter()
                    }/*)*/
                }

                override fun onError(e: Throwable) {
                    /*characters.value = */when (e) {
                        is IOException -> {
                            /*           Resource.Error<List<Character>>(*/
                            Log.d(
                                TAG, e.localizedMessage
                                    ?: "Server not reached. Check internet connection"
                            )
                            /* )*/
                        }
                        else -> {
//                            Resource.Error<List<Character>>(
                            Log.d(TAG, e.localizedMessage ?: "Unexpected error occurred")
//                            )
                        }
                    }
                }
            })
        return characters
    }
}