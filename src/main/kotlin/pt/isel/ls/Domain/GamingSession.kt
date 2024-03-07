package pt.isel.ls.Domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class GamingSession(
    val id: Int,
    val game: Int,
    val capacity: Int,
    val startingDate: LocalDate,
)
