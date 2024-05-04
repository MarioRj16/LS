package pt.isel.ls.data.mem

import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session

abstract class DataMemSchema {
    val playersDB = DataMemTable<Player>()

    val gamesDB = DataMemTable<Game>()

    val genreDB = DataMemTable<Genre>()

    val sessionsDB = DataMemTable<Session>()
}
