package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.isFuture
import pt.isel.ls.utils.toLocalDateTime
import pt.isel.ls.utils.toLong

@Serializable
data class SessionUpdate(val capacity: Int, val startingDate: Long) {
    init {
        require(capacity >= 2) { "Capacity must be greater than 1" }
    }

    val startingDateFormatted:LocalDateTime
        get(){
            val final = startingDate.toLocalDateTime()
            require(final.isFuture()) { "Starting date must be in the future" }
            return final
        }


    companion object {
        operator fun invoke(session: Session): SessionUpdate {
            return SessionUpdate(session.maxCapacity, session.startingDate.toLong())
        }
    }
}
