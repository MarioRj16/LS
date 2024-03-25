package pt.isel.ls.data.mem

import pt.isel.ls.domain.Game
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Player

abstract class DataMemSchema {
    val playersDB = DataMemTable<Player>()

    val gamesDB = DataMemTable<Game>()

    val gamingSessionsDB = DataMemTable<GamingSession>()
}
