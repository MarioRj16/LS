package pt.isel.ls.Domain

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val name: String,
    val developer: String,
    val genres: Set<Genre>
)