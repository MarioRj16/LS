package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.SessionStorage
import pt.isel.ls.models.GamingSession
import java.util.*

class SessionMem(val schema: DataMemSchema): SessionStorage {
    override fun create(capacity: Int, game: Int, date: Date): Int {
        TODO("Not yet implemented")
    }

    override fun get(sessionId: Int): GamingSession {
        TODO("Not yet implemented")
    }

    override fun search(game: Int, date: String?, isOpen: Boolean?, player: Int?): Set<GamingSession> {
        TODO("Not yet implemented")
    }


    override fun addPlayer(session: Int, player: Int){
        TODO("Not yet implemented")
    }
}