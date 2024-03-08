package pt.isel.ls.Data.Mem

import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.GamingSession
import pt.isel.ls.Domain.Player

class DataMemSchema {
    val playersDB = HashMap<Int, Player>()
    var playersNextId = 1

    val gamesDB = HashMap<Int, Game>()
    var gamesNextId = 1

    val gamingSessionsDB = HashMap<Int, GamingSession>()
    var gamingSessionsNextId = 1
}
