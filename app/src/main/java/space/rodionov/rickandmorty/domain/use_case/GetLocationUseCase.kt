package space.rodionov.rickandmorty.domain.use_case

import space.rodionov.rickandmorty.domain.repository.RamRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repository: RamRepository
) {

    operator fun invoke(locId: Int) = repository.getLocationById(locId)
}