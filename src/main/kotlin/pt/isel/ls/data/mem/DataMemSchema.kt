package pt.isel.ls.data.mem

import pt.isel.ls.domain.*

abstract class DataMemSchema {
    val playersDB = DBTableMem<Player>()

    val gamesDB = DBTableMem<Game>()

    val gamingSessionsDB = DBTableMem<GamingSession>()

    var genresDB = setOf<Genre>()
        private set
}