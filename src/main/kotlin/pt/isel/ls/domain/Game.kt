package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val name: String,
    val developer: String,
    val genres: Set<Genre>
){
    init {
        require(id >= 1){"ID must be a positive integer\nid=$id"}
        require(name.isNotBlank()){"Name cannot be blank"}
        require(developer.isNotBlank()){"Developer cannot be blank"}
        require(genres.isNotEmpty()){"Games has to have at least one genre"}
    }
}