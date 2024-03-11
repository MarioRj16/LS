package pt.isel.ls.Domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SessionCreate(val gameId: Int, val capacity: Int, val startingDate: LocalDateTime)