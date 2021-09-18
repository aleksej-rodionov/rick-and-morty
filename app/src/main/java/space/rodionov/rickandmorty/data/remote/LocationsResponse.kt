package space.rodionov.rickandmorty.data.remote

import space.rodionov.rickandmorty.data.remote.dto.LocationDto

data class LocationsResponse(
    val results: List<LocationDto>
)