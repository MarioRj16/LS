package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Data.Storage

class DataMem(schema: DataMemSchema): Storage {
    override val players: PlayerStorage = PlayersMem(schema)

    override val gamingSessions: GamingSessionStorage = GamingSessionMem(schema)

    override val games: GameStorage = GamesMem(schema)
}