package pt.isel.ls.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.isPast

@Serializable
data class SessionCreate(val gameId: Int, val capacity: Int, val startingDate: LocalDateTime) {
    init {
        require(capacity >= 1) { "The session capacity has to be at least 1" }
        require(!startingDate.isPast()) {
            "The session starting date cannot precede the current LocalDateTime"
        }
    }
}
