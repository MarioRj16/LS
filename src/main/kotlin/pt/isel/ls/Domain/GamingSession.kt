package pt.isel.ls.Domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GamingSession(
    val id: Int,
    val game: Int,
    val capacity: Int,
    val startingDate: LocalDateTime,
    val players: Set<Player>
)
