package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.isFuture
import pt.isel.ls.utils.toLocalDateTime

@Serializable
data class SessionCreate(val gameId: Int, val capacity: Int, val startingDate: Long) {
    init {
        require(capacity >= 2) { "The session capacity has to be at least 2" }
    }
    val startingDateFormatted: LocalDateTime
        get() {
            val final = startingDate.toLocalDateTime()
            require(final.isFuture()) { "Starting date must be in the future" }
            return final
        }
}
