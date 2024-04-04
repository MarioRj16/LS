package pt.isel.ls.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class SessionUpdate(val capacity: Int, val startingDate: LocalDateTime)
