package space.rodionov.rickandmorty.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import space.rodionov.rickandmorty.data.remote.dto.CharacterDto
import space.rodionov.rickandmorty.data.remote.dto.EpisodeDto
import space.rodionov.rickandmorty.data.remote.dto.LocationDto

interface RickAndMortyApi {

    @GET("/character")
    suspend fun getCharacters(): CharactersResponse

    @GET("/character/{charId}")
    suspend fun getCharacterById(
        @Path("charId") charId: Int
    ): CharacterDto

    @GET("/location")
    suspend fun getLocations(): LocationsResponse

    @GET("/location/{locId}")
    suspend fun getLocationById(
        @Path("locId") locId: Int
    ): LocationDto

    @GET("/episode")
    suspend fun getEpisodes() : EpisodesResponse

    @GET("/episode/{epiId}")
    suspend fun getEpisodeById(
        @Path("epiId") epiId: Int
    ) : EpisodeDto

}





