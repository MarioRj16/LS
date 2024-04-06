package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.isFuture

@Serializable
data class SessionUpdate(val capacity: Int, val startingDate: LocalDateTime) {
    init {
        require(capacity >= 2) { "Capacity must be greater than 1" }
        require(startingDate.isFuture()) { "Starting date must be in the future" }
    }
}
