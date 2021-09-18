package space.rodionov.rickandmorty.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto

interface RickAndMortyApi {

    @GET("/api/character")
    fun getCharacters(
        @Query("page") page: Int
    ): Single<CharactersResponse>

    @GET("/api/character/{charId}")
    fun getCharacterById(
        @Path("charId") charId: Int
    ): Single<CharacterDto>

    @GET("/api/location")
    fun getLocations(
        @Query("page") page: Int
    ): Single<LocationsResponse>

    @GET("/api/location/{locId}")
    fun getLocationById(
        @Path("locId") locId: Int
    ): Single<LocationDto>

    @GET("/api/episode")
    fun getEpisodes(
        @Query("page") page: Int
    ): Single<EpisodesResponse>

    @GET("/api/episode/{epiId}")
    fun getEpisodeById(
        @Path("epiId") epiId: Int
    ): Single<EpisodeDto>

}





