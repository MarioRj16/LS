package pt.isel.ls.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SessionUpdate(val capacity: Int, val startingDate: LocalDateTime)
