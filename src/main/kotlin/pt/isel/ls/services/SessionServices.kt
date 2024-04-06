package pt.isel.ls.services

import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionResponse
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.Data
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.isPast
import java.util.*

open class SessionServices(internal val db: Data) : ServicesSchema(db) {
    fun searchSessions(
        sessionSearch: SessionSearch,
        token: UUID,
        skip: Int,
        limit: Int,
    ): List<GamingSession> = withAuthorization(token) {
        return@withAuthorization db.gamingSessions.search(sessionSearch, limit, skip)
    }

    fun createSession(
        sessionInput: SessionCreate,
        token: UUID,
    ): SessionResponse = withAuthorization(token) { user ->
        val session =
            db.gamingSessions.create(
                sessionInput.capacity,
                sessionInput.gameId,
                sessionInput.startingDate,
                user.id,
            )
        return@withAuthorization SessionResponse(session.id)
    }

    fun getSession(
        sessionId: Int,
        token: UUID,
    ): GamingSession = withAuthorization(token) {
        return@withAuthorization db.gamingSessions.get(sessionId)
    }

    fun updateSession(
        sessionId: Int,
        sessionUpdate: SessionUpdate,
        token: UUID,
    ): GamingSession = withAuthorization(token) { user ->
        require(!sessionUpdate.startingDate.isPast()) { "LocalDateTime cannot be in past" }
        val currentSession = db.gamingSessions.get(sessionId)
        if (user.id != currentSession.hostId) {
            throw ForbiddenException("Changes can only be made by the creator of the session")
        }
        require(currentSession.players.size <= sessionUpdate.capacity) {
            "Cannot update session capacity to a value lower than the number of players currently in session"
        }

        return@withAuthorization db.gamingSessions.update(sessionId, sessionUpdate)
    }

    fun deleteSession(
        sessionId: Int,
        token: UUID,
    ) = withAuthorization(token) { user ->
        if (db.gamingSessions.isOwner(sessionId, user.id)) {
            return@withAuthorization db.gamingSessions.delete(sessionId)
        }

        throw ForbiddenException("You can only delete games you created")
    }

    fun addPlayerToSession(
        sessionId: Int,
        token: UUID,
    ): Int = withAuthorization(token) { user ->
        db.gamingSessions.addPlayer(sessionId, user.id)
        return@withAuthorization user.id
    }

    fun removePlayerFromSession(
        sessionId: Int,
        token: UUID,
        playerId: Int,
    ) = withAuthorization(token) { user ->
        val session = db.gamingSessions.get(sessionId)
        if (session.hostId != user.id) {
            throw ForbiddenException("Changes can only be made by the creator of the session")
        }
        db.gamingSessions.removePlayer(sessionId, playerId)
    }
}
