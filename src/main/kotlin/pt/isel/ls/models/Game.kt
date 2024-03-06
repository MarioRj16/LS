package pt.isel.ls.models

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val ID: Int,
    val name: String,
    val developer: String,
)