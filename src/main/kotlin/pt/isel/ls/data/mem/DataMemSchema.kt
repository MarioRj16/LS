package pt.isel.ls.data.mem

import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session

abstract class DataMemSchema {
    val playersDB = DataMemTable<Player>()

    val gamesDB = DataMemTable<Game>()

    private val genres = setOf(
        Genre(1, "Action"),
        Genre(2, "Adventure"),
        Genre(3, "RPG"),
        Genre(4, "Simulation"),
        Genre(5, "Strategy")
    )
    val genreDB = genres.associateBy { it.genreId }

    val sessionsDB = DataMemTable<Session>()
}
