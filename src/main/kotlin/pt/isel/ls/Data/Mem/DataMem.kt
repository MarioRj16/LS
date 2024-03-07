package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Data.Storage

class DataMem(val schema: DataMemSchema): Storage {
    override val players: PlayerStorage
        get() = TODO("Not yet implemented")
    override val gamingSessions: GamingSessionStorage
        get() = TODO("Not yet implemented")
    override val games: GameStorage
        get() = TODO("Not yet implemented")
}