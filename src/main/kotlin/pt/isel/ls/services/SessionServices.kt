package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionResponse
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.api.models.SessionUpdate
import pt.isel.ls.data.Data
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.isPast

open class SessionServices(internal val db: Data) : ServicesSchema() {
    fun searchSessions(
        input: String,
        authorization: String?,
        skip: Int?,
        limit: Int?,
    ): List<GamingSession> {
        bearerToken(authorization, db).id
        val sessionInput = Json.decodeFromString<SessionSearch>(input)
        return db.gamingSessions.search(
            sessionInput.game,
            sessionInput.date,
            sessionInput.state,
            sessionInput.playerId,
            limit ?: DEFAULT_LIMIT,
            skip ?: DEFAULT_SKIP,
        )
    }

    fun createSession(
        input: String,
        authorization: String?,
    ): SessionResponse {
        val user = bearerToken(authorization, db)
        val sessionInput = Json.decodeFromString<SessionCreate>(input)
        val session =
            db.gamingSessions.create(
                sessionInput.capacity,
                sessionInput.gameId,
                sessionInput.startingDate,
                user.id,
            )
        return SessionResponse(session.id)
    }

    fun getSession(
        sessionId: Int?,
        authorization: String?,
    ): GamingSession {
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        bearerToken(authorization, db)
        return db.gamingSessions.get(sessionId)
    }

    fun updateSession(
        sessionId: Int?,
        input: String,
        authorization: String?
    ): GamingSession{
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        val user = bearerToken(authorization, db)
        val sessionUpdate = Json.decodeFromString<SessionUpdate>(input)
        require(!sessionUpdate.startingDate.isPast()){ "LocalDateTime cannot be in past" }
        val currentSession = db.gamingSessions.get(sessionId)
        if(user.id != currentSession.creatorId)
            throw ForbiddenException("Changes can only be made by the creator of the session")
        require(currentSession.players.size <= sessionUpdate.capacity){
            "Cannot update session capacity to a value lower than the number of players currently in session"
        }

        return db.gamingSessions.update(sessionId, sessionUpdate.startingDate, sessionUpdate.capacity)
    }

    fun deleteSession(
        sessionId: Int?,
        authorization: String?,
    ) {
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        val user = bearerToken(authorization, db)
        if (db.gamingSessions.isOwner(sessionId, user.id)) {
            return db.gamingSessions.delete(sessionId)
        }

        throw ForbiddenException("You can only delete games you created")
    }

    fun addPlayerToSession(
        sessionId: Int?,
        authorization: String?,
    ): Int {
        requireNotNull(sessionId) { "Invalid argument id can't be null" }
        val playerId = bearerToken(authorization, db).id
        db.gamingSessions.addPlayer(sessionId, playerId)
        return playerId
    }
}
