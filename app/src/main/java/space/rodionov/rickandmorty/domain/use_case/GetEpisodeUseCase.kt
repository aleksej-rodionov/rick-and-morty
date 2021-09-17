package space.rodionov.rickandmorty.domain.use_case

import space.rodionov.rickandmorty.domain.repository.RamRepository
import javax.inject.Inject

class GetEpisodeUseCase @Inject constructor(
    private val repository: RamRepository
) {

    operator fun invoke(epiId: Int) = repository.getEpisodeById(epiId)
}