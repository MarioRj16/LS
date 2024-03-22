package pt.isel.ls.data.mem

import pt.isel.ls.data.GameStorage
import pt.isel.ls.data.GamingSessionStorage
import pt.isel.ls.data.PlayerStorage
import pt.isel.ls.data.Storage

open class DataMem : Storage, DataMemSchema() {
    override fun reset() {
        playersDB.table.clear()
        gamingSessionsDB.table.clear()
        gamesDB.table.clear()
    }

    override fun populate() {
        TODO("Not yet implemented")
    }

    override val players: PlayerStorage = PlayersMem(playersDB)

    override val gamingSessions: GamingSessionStorage = GamingSessionMem(gamingSessionsDB, playersDB, gamesDB)

    override val games: GameStorage = GamesMem(gamesDB)
}