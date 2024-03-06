package pt.isel.ls.models

import kotlinx.serialization.Serializable

@Serializable
data class GamingSession(
    val ID: Int,
    val game: Int,
    val capacity: Int,
    val firstDate: String,
    val isOpen: Boolean
)