package pt.isel.ls.data

import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionResponse
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.PaginatedResponse

interface SessionsData {
    fun create(
        sessionInput: SessionCreate,
        hostId: Int,
    ): Session

    fun get(sessionId: Int): Session?

    fun search(
        sessionParameters: SessionSearch,
        limit: Int,
        skip: Int,
    ): PaginatedResponse<SessionResponse>

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
