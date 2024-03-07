package pt.isel.ls.Data

import pt.isel.ls.Domain.GamingSession
import java.util.*

interface SessionStorage {

    fun create(capacity: Int, game: Int, date: Date): Int

    fun get(sessionId: Int): GamingSession?

    fun search(game: Int, date: String?, isOpen: Boolean?, player: Int?): Set<GamingSession>

    fun addPlayer(session: Int, player: Int)
}