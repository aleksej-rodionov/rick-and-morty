package space.rodionov.rickandmorty.domain.use_case

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import space.rodionov.rickandmorty.data.remote.CharactersResponse
import space.rodionov.rickandmorty.data.remote.dto.toCharacter
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.repository.RamRepository
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LOGS"

class GetCharactersUseCase @Inject constructor(
    private val repository: RamRepository
) {
//    private val composite = CompositeDisposable()
//    val tempList = mutableListOf<Character>()
//    val newCharacters = MutableLiveData<List<Character>>()
//
//    operator fun invoke(nextPage: Int): LiveData<List<Character>> {
//        doSmth(nextPage)
//        Log.d(TAG, "tempList.size = ${tempList.size}")
//        return  newCharacters
//    }
//
//        private fun doSmth(nextPage: Int) {
//        val output = repository.getCharacters(nextPage)
//        output.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<CharactersResponse> {
//                override fun onSubscribe(d: Disposable) {
//                    composite.add(d)
//                }
//
//                override fun onSuccess(t: CharactersResponse) {
//                    tempList.clear()
//                    tempList.addAll(t.results.map {
//                        it.toCharacter()
//                    })
//                    newCharacters.value = tempList
//                    Log.d(TAG, "onSuccess: ${t.results.size}")
//                }
//
//                override fun onError(e: Throwable) {
//                    when (e) {
//                        is IOException -> {
//                            Log.d(
//                                TAG, e.localizedMessage
//                                    ?: "Server not reached. Check internet connection"
//                            )
//                        }
//                        else -> {
//                            Log.d(TAG, e.localizedMessage ?: "Unexpected error occurred")
//                        }
//                    }
//                }
//            })
//    }

    operator fun invoke(nextPage: Int) = repository.getCharacters(nextPage)
}