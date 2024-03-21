package pt.isel.ls.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.currentLocalDateTime
import pt.isel.ls.utils.isPast

@Serializable
data class GamingSession(
    val id: Int,
    val gameId: Int,
    val maxCapacity: Int,
    val startingDate: LocalDateTime,
    val players: Set<Player>
){
    //TODO: Create an annotation class to automate PK and FK checks
    init {
        require(id >= 1){"ID must be a positive integer\nid=$id"}
        require(gameId >= 1){"gameId must be a positive integer\nid=$id"}
        require(maxCapacity > 1){"The max capacity of a gaming session must be higher than 1\nmaxCapacity=$maxCapacity"}
        require(!startingDate.isPast()){
            "The starting date of a gaming session must not be in the past\nstartingDate=$startingDate"
        }
    }
    val state: Boolean
        get() = startingDate >= currentLocalDateTime() && players.size < maxCapacity
}
