package space.rodionov.rickandmorty.domain.model

data class Location(
    val dimension: String,
    val id: Int,
    val name: String,
    val type: String
)