package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SessionCreate(val gameId: Int, val capacity: Int, val startingDate: LocalDateTime)
