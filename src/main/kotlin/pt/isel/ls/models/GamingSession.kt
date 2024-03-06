package pt.isel.ls.models

import java.util.*

data class GamingSession(
    val ID: Int,
    val game: Int,
    val capacity: Int,
    val startingDate: Date,
    val isOpen: Boolean
)