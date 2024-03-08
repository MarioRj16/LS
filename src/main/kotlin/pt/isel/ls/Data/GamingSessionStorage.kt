package pt.isel.ls.Data

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.Domain.GamingSession

interface GamingSessionStorage {

    fun create(capacity: Int, game: Int, date: LocalDateTime): GamingSession

    fun get(sessionId: Int): GamingSession?

    fun search(game: Int, date: LocalDateTime?, isOpen: Boolean?, player: Int?): List<GamingSession>

    fun addPlayer(session: Int, player: Int): Boolean
}