package pt.isel.ls.data.mem

import pt.isel.ls.domain.Game
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player

abstract class DataMemSchema {
    val playersDB = DBTableMem<Player>()

    val gamesDB = DBTableMem<Game>()

    val gamingSessionsDB = DBTableMem<GamingSession>()

    var genresDB = setOf<Genre>()
        private set
}
