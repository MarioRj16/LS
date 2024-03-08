package pt.isel.ls.Data.Mem

import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.GamingSession
import pt.isel.ls.Domain.Player

abstract class DataMemSchema {
    val playersDB = DBTableMem<Player>()

    val gamesDB = DBTableMem<Game>()

    val gamingSessionsDB = DBTableMem<GamingSession>()
}
