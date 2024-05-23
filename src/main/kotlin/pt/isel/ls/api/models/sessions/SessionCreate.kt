package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.toLocalDateTime

@Serializable
data class SessionCreate (val gameId: Int, val capacity: Int, val startingDate: LocalDateTime) {
    companion object Factory {
        operator fun invoke(gameId: Int, capacity: Int, startingDate: Long): SessionCreate {
            val date = startingDate.toLocalDateTime()
            return SessionCreate(gameId, capacity, date)
        }
    }
}
