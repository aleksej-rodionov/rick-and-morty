package space.rodionov.rickandmorty.presentation.character.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import space.rodionov.rickandmorty.common.Resource
import space.rodionov.rickandmorty.domain.use_case.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    fun getCharacters() = getCharactersUseCase()
}





