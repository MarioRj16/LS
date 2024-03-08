package pt.isel.ls.Domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class SessionCreate(val gameId: Int, val capacity: Int, val startingDate: LocalDate)