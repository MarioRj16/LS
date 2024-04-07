package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.isFuture

@Serializable
data class SessionUpdate constructor(val capacity: Int, val startingDate: LocalDateTime) {
    init {
        require(capacity >= 2) { "Capacity must be greater than 1" }
        require(startingDate.isFuture()) { "Starting date must be in the future" }
    }

    companion object {
        operator fun invoke(session: Session): SessionUpdate {
            return SessionUpdate(session.maxCapacity, session.startingDate)
        }
    }

}
