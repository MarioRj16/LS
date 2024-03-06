package pt.isel.ls.Data.Mem

import pt.isel.ls.models.*
import java.util.*

class DataMemSchema {
    val playersDB = HashMap<Int, Player>()
    var playersNextId = 1

    val gamesDB = HashMap<Int, Game>()
    var gamesNextId = 1

    val gamingSessionsDB = HashMap<Int, GamingSession>()
    var gamingSessionsNextId = 1

    val genresDB = setOf<Genre>()

    val tokensDB = HashMap<UUID, Token>()

    val gamesGenresDB = setOf<GameAndGenre>()

    val playersSessionsDB = setOf<PlayerSession>()

}
