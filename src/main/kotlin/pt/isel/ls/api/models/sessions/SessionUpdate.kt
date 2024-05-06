package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.toLocalDateTime
import pt.isel.ls.utils.toLong

@Serializable
data class SessionUpdate(val capacity: Int, val startingDate: Long) {
    val startingDateFormatted:LocalDateTime
        get(){
            return startingDate.toLocalDateTime()
        }

    companion object {
        operator fun invoke(session: Session): SessionUpdate {
            return SessionUpdate(session.maxCapacity, session.startingDate.toLong())
        }
    }
}
