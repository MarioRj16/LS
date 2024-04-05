package pt.isel.ls.services

import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionResponse
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.api.models.SessionUpdate
import pt.isel.ls.data.Data
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.isPast

open class SessionServices(internal val db: Data) : ServicesSchema(db) {
    fun searchSessions(
        sessionSearch: SessionSearch,
        authorization: String?,
        skip: Int,
        limit: Int,
    ): List<GamingSession> = withAuthorization(authorization) {
        return@withAuthorization db.gamingSessions.search(sessionSearch, limit, skip)
    }

    fun createSession(
        sessionInput: SessionCreate,
        authorization: String?,
    ): SessionResponse = withAuthorization(authorization){ user ->
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
        sessionId: Int?,
        authorization: String?,
    ): GamingSession = withAuthorization(authorization){
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        return@withAuthorization db.gamingSessions.get(sessionId)
    }

    fun updateSession(
        sessionId: Int?,
        sessionUpdate: SessionUpdate,
        authorization: String?,
    ): GamingSession = withAuthorization(authorization) { user ->
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        require(!sessionUpdate.startingDate.isPast()) { "LocalDateTime cannot be in past" }
        val currentSession = db.gamingSessions.get(sessionId)
        if (user.id != currentSession.creatorId) {
            throw ForbiddenException("Changes can only be made by the creator of the session")
        }
        require(currentSession.players.size <= sessionUpdate.capacity) {
            "Cannot update session capacity to a value lower than the number of players currently in session"
        }

        return@withAuthorization db.gamingSessions.update(sessionId, sessionUpdate)
    }

    fun deleteSession(
        sessionId: Int?,
        authorization: String?,
    ) = withAuthorization(authorization) { user ->
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        if (db.gamingSessions.isOwner(sessionId, user.id)) {
            return@withAuthorization db.gamingSessions.delete(sessionId)
        }

        throw ForbiddenException("You can only delete games you created")
    }

    fun addPlayerToSession(
        sessionId: Int?,
        authorization: String?,
    ): Int = withAuthorization(authorization){ user ->
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        db.gamingSessions.addPlayer(sessionId, user.id)
        return@withAuthorization user.id
    }

    fun removePlayerFromSession(
        sessionId: Int?,
        authorization: String?,
        playerId: Int?,
    ) = withAuthorization(authorization){ user ->
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        requireNotNull(playerId) { "Invalid argument id can't be null" }
        val session = db.gamingSessions.get(sessionId)
        if (session.creatorId != user.id) {
            throw ForbiddenException("Changes can only be made by the creator of the session")
        }
        db.gamingSessions.removePlayer(sessionId, playerId)
    }
}
