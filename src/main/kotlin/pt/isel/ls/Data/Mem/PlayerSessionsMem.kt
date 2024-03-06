package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.PlayerSessionsStorage
import pt.isel.ls.models.PlayerSession

class PlayerSessionsMem(val schema: DataMemSchema): PlayerSessionsStorage {
    override fun create(playerId: Int, sessionId: Int): PlayerSession {
        TODO("Not yet implemented")
    }

    override fun search(playerId: Int, sessionId: Int): Set<PlayerSession> {
        TODO("Not yet implemented")
    }
}