package space.rodionov.rickandmorty.data.remote

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto

interface RickAndMortyApi {

    @GET("/character")
    fun getCharacters(): Single<CharactersResponse>

    @GET("/character/{charId}")
    fun getCharacterById(
        @Path("charId") charId: Int
    ): Single<CharacterDto>

    @GET("/location")
    fun getLocations(): Single<LocationsResponse>

    @GET("/location/{locId}")
    fun getLocationById(
        @Path("locId") locId: Int
    ): Single<LocationDto>

    @GET("/episode")
    fun getEpisodes(): Single<EpisodesResponse>

    @GET("/episode/{epiId}")
    fun getEpisodeById(
        @Path("epiId") epiId: Int
    ): Single<EpisodeDto>

}





