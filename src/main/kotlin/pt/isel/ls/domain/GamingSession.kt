package pt.isel.ls.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.currentLocalDateTime

@Serializable
data class GamingSession(
    val id: Int,
    val game: Int,
    val maxCapacity: Int,
    val startingDate: LocalDateTime,
    val players: Set<Player>
){
    val state: Boolean
        get() = startingDate >= currentLocalDateTime() && players.size < maxCapacity
}
