package space.rodionov.rickandmorty.domain.use_case

import space.rodionov.rickandmorty.domain.repository.RamRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: RamRepository
) {

    operator fun invoke(charId: Int) = repository.getCharacterById(charId)
}