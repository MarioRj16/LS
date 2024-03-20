package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionResponse
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.*
import pt.isel.ls.utils.bearerToken

open class SessionServices(internal val db:Storage) {

    fun searchSessions(input: String,authorization:String?,skip:Int?,limit:Int?):List<GamingSession> {
        bearerToken(authorization,db).id
        val sessionInput = Json.decodeFromString<SessionSearch>(input)
        return db.gamingSessions.search(
            sessionInput.game,
            sessionInput.date,
            sessionInput.state,
            sessionInput.playerId,
            limit ?: DEFAULT_LIMIT,
            skip ?: DEFAULT_SKIP
        )
    }

    fun createSession(input: String,authorization:String?): SessionResponse {
        bearerToken(authorization,db).id
        /**
         * TODO
         * We could add here the person who created the session to list of players
         */
        val sessionInput = Json.decodeFromString<SessionCreate>(input)
        val session=db.gamingSessions.create(
            sessionInput.capacity,
            sessionInput.gameId,
            sessionInput.startingDate
        )
        return SessionResponse(session.id)
    }

    fun getSession(id: Int?,authorization:String?):GamingSession {
        requireNotNull(id)
        // TODO("Write a message")
        bearerToken(authorization,db).id
        return db.gamingSessions.get(id)
    }

    fun addPlayerToSession(sessionId: Int?, authorization:String?):Int {
        requireNotNull(sessionId){"id"}
        // TODO: Improve this message
        val playerId = bearerToken(authorization, db).id
        db.gamingSessions.addPlayer(sessionId, playerId)
        return playerId
    }
}