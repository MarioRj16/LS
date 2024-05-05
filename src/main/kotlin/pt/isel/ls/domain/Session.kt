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
    val currentCapacity: Int = players.size
) {
    init {
        require(id >= 1) { "ID must be a positive integer\nid=$id" }
        require(gameId >= 1) { "gameId must be a positive integer\nid=$id" }
        require(maxCapacity > 1) { "The max capacity of a gaming session must be higher than 1\nmaxCapacity=$maxCapacity" }
        require(hostId >= 1) { "hostId must be a positive integer\nhostId=$hostId" }
        require(maxCapacity >= players.size) {
            "The number of players in a gaming session can not exceed the number of players in session"
        }
        require(currentCapacity >= 0) { "currentCapacity must be a positive integer\nid=$currentCapacity" }
        require(currentCapacity <= maxCapacity) { "currentCapacity must be less than or equal to maxCapacity\nid=$currentCapacity" }
    }

    val state: Boolean
        get() = !startingDate.isPast() && players.size < maxCapacity
}
