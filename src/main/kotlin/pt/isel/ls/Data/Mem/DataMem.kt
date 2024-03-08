package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Data.Storage

open class DataMem: Storage, DataMemSchema() {
    override val players: PlayerStorage = PlayersMem(playersDB)

    override val gamingSessions: GamingSessionStorage = GamingSessionMem(gamingSessionsDB, playersDB, gamesDB)

    override val games: GameStorage = GamesMem(gamesDB)
}