package pt.isel.ls.data

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.domain.GamingSession

interface GamingSessionsData {
    fun create(
        capacity: Int,
        game: Int,
        date: LocalDateTime,
        playerId: Int,
    ): GamingSession

    fun get(sessionId: Int): GamingSession

    fun search(
        game: Int,
        date: LocalDateTime?,
        isOpen: Boolean?,
        player: Int?,
        limit: Int,
        skip: Int,
    ): List<GamingSession>

    fun update(sessionId: Int, newDateTime: LocalDateTime, newCapacity: Int): GamingSession

    fun delete(sessionId: Int)

    fun addPlayer(
        session: Int,
        player: Int,
    )

    fun removePlayer(
        sessionId: Int,
        playerId: Int,
    )

    fun isOwner(
        sessionId: Int,
        playerId: Int,
    ): Boolean
}
