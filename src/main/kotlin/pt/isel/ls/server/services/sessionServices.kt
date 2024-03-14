package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Data.Storage
import pt.isel.ls.Domain.*

class SessionServices(private val db:Storage) {

    fun searchSessions(input: String):List<GamingSession> {
        val sessionInput = Json.decodeFromString<SessionSearch>(input)
        return db.gamingSessions.search(
            sessionInput.game,
            sessionInput.date,
            sessionInput.state,
            sessionInput.playerId
        )
    }

    fun createSession(input: String):SessionResponse {
        val sessionInput = Json.decodeFromString<SessionCreate>(input)
        val session=db.gamingSessions.create(
            sessionInput.capacity,
            sessionInput.gameId,
            sessionInput.startingDate
        )
        return SessionResponse(session.id)
    }

    fun getSession(id: Int?):GamingSession? {
        require(id!=null)
        return db.gamingSessions.get(id)
    }

    fun addPlayerToSession(id: Int?, input: String?):Boolean {
        require(id!=null && input !=null)
        /**
         * Maybe change sessionResponse since it really isn't the session response yet
         */
        val playerId = Json.decodeFromString<SessionResponse>(input).id
        return db.gamingSessions.addPlayer(id,playerId)
    }
}