package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionResponse
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.*

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

    fun createSession(input: String): SessionResponse {
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

    fun addPlayerToSession(id: Int?, input: String?):Int {
        require(id!=null){"id"}
        require(input !=null){"input"}
        /**
         * Maybe change sessionResponse since it really isn't the session response yet
         */
        val playerId = Json.decodeFromString<SessionResponse>(input).id
        db.gamingSessions.addPlayer(id,playerId)
        return playerId
    }
}