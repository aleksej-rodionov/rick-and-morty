package space.rodionov.rickandmorty.domain.use_case

import space.rodionov.rickandmorty.domain.repository.RamRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val repository: RamRepository
) {

    operator fun invoke(page: Int) = repository.getEpisodes(page)
}