package space.rodionov.rickandmorty.domain.use_case

import io.reactivex.Flowable
import retrofit2.HttpException
import space.rodionov.rickandmorty.common.Resource
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.domain.repository.RamRepository
import java.io.IOException
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: RamRepository
) {

    operator fun invoke() {
        //todo
    }
}