package pt.isel.ls.data

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.domain.GamingSession

interface GamingSessionsData {

    fun create(capacity: Int, game: Int, date: LocalDateTime): GamingSession

    fun get(sessionId: Int): GamingSession

    fun search(
        game: Int,
        date: LocalDateTime?,
        isOpen: Boolean?,
        player: Int?,
        limit: Int,
        skip: Int
    ): List<GamingSession>

    fun addPlayer(session: Int, player: Int)
}