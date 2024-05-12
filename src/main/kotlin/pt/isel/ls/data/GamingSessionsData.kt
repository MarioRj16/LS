package pt.isel.ls.data

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.api.models.sessions.SessionListResponse
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.domain.Session

interface GamingSessionsData {
    fun create(
        capacity: Int,
        game: Int,
        date: LocalDateTime,
        hostId: Int,
    ): Session

    fun get(sessionId: Int): Session?

    fun search(
        sessionParameters: SessionSearch,
        limit: Int,
        skip: Int,
    ): SessionListResponse

    fun update(sessionId: Int, sessionUpdate: SessionUpdate)

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
