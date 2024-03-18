package pt.isel.ls.data

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.domain.GamingSession

interface GamingSessionStorage {

    fun create(capacity: Int, game: Int, date: LocalDateTime): GamingSession

    fun get(sessionId: Int): GamingSession

    fun search(game: Int, date: LocalDateTime?, isOpen: Boolean?, player: Int?, limit: Int, skip: Int): List<GamingSession>

    fun addPlayer(session: Int, player: Int)
}