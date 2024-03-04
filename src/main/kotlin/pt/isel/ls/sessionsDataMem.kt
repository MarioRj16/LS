package pt.isel.ls

import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val ID: Int,
    val name: String,
    val email: String,
    val token: String = "${UUID.randomUUID()}"
)

@Serializable
data class Genre(val name: String)

@Serializable
data class GameAndGenre(val game: Int, val genre: Int)

@Serializable
data class Game(
    val ID: Int,
    val name: String,
    val developer: String,
    val maxCapacity: Int
)

@Serializable
data class GamingSessions(
    val ID: Int,
    val game: Int,
    val capacity: Int,
    val firstDate: String,
    val isOpen: Boolean
)

@Serializable
data class PlayerSession(val player: Int, val gamingSession: Int)
