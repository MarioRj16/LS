package pt.isel.ls.domain

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.utils.isPast

data class Session(
    val id: Int,
    val gameId: Int,
    val hostId: Int,
    val maxCapacity: Int,
    val startingDate: LocalDateTime,
    val players: Set<Player>,
) {
    init {
        require(id >= 1) { "ID must be a positive integer\nid=$id" }
        require(gameId >= 1) { "gameId must be a positive integer\nid=$id" }
        require(maxCapacity > 1) { "The max capacity of a gaming session must be higher than 1\nmaxCapacity=$maxCapacity" }
        require(maxCapacity >= players.size) {
            "The number of players in a gaming session can not exceed the number of players in session"
        }
    }

    val state: Boolean
        get() = !startingDate.isPast() && players.size < maxCapacity
}
