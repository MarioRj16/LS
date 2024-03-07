package pt.isel.ls.Data.Mem

import pt.isel.ls.Domain.*

class DataMemSchema {
    val playersDB = HashMap<Int, Player>()
    var playersNextId = 1

    val gamesDB = HashMap<Int, Game>()
    var gamesNextId = 1

    val gamingSessionsDB = HashMap<Int, GamingSession>()
    var gamingSessionsNextId = 1

    val genresDB = mutableSetOf<Genre>()

    val gamesGenresDB = mutableSetOf<GameAndGenre>()

    val playersSessionsDB = mutableSetOf<PlayerSession>()
}
