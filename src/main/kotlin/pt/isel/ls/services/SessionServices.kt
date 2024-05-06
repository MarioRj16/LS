package pt.isel.ls.services

import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionCreateResponse
import pt.isel.ls.api.models.sessions.SessionDetails
import pt.isel.ls.api.models.sessions.SessionListResponse
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.Data
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.isPast
import java.util.*

open class SessionServices(internal val db: Data) : ServicesSchema(db) {
    fun searchSessions(
        sessionSearch: SessionSearch,
        token: UUID,
        skip: Int,
        limit: Int,
    ): SessionListResponse = withAuthorization(token) {
        val sessions = db.gamingSessions.search(sessionSearch, limit, skip)
        return@withAuthorization SessionListResponse(sessions)
    }

    fun createSession(
        sessionInput: SessionCreate,
        token: UUID,
    ): SessionCreateResponse = withAuthorization(token) { user ->
        require(db.games.get(sessionInput.gameId) != null) { "The provided game does not exist" }
        println(sessionInput.startingDateFormatted)
        val session =
            db.gamingSessions.create(
                sessionInput.capacity,
                sessionInput.gameId,
                sessionInput.startingDateFormatted,
                user.id,
            )
        return@withAuthorization SessionCreateResponse(session.id)
    }

    fun getSession(
        sessionId: Int,
        token: UUID,
    ): SessionDetails = withAuthorization(token) {
        val session = db.gamingSessions.get(sessionId)
            ?: throw NoSuchElementException("No gaming session with id $sessionId was found")
        // This second DB call is made so both the game's name and id can be returned
        val game = db.games.get(session.gameId)!!
        return@withAuthorization SessionDetails(session, game)
    }

    fun updateSession(
        sessionId: Int,
        sessionUpdate: SessionUpdate,
        token: UUID,
    ): SessionUpdate = withAuthorization(token) { user ->
        val session = db.gamingSessions.get(sessionId)
            ?: throw NoSuchElementException("No session $sessionId was found")
        if (!session.state) {
            throw ForbiddenException("Cannot update a closed session")
        }
        if (session.startingDate.isPast()) {
            throw ForbiddenException("Cannot update a session that has already started")
        }
        if (user.id != session.hostId) {
            throw ForbiddenException("Changes can only be made by the creator of the session")
        }
        if (sessionUpdate.capacity < session.players.size) {
            throw IllegalArgumentException("Cannot reduce capacity to less than the number of players in the session")
        }
        db.gamingSessions.update(sessionId, sessionUpdate)
        return@withAuthorization SessionUpdate(db.gamingSessions.get(sessionId)!!)
    }

    fun deleteSession(
        sessionId: Int,
        token: UUID,
    ) = withAuthorization(token) { user ->
        val session = db.gamingSessions.get(sessionId)
            ?: throw NoSuchElementException("No session $sessionId was found")

        if (session.hostId != user.id) {
            throw ForbiddenException("You can only delete games you created")
        }
        return@withAuthorization db.gamingSessions.delete(sessionId)
    }

    fun addPlayerToSession(
        sessionId: Int,
        token: UUID,
    ): Int = withAuthorization(token) { user ->
        val session = db.gamingSessions.get(sessionId)
            ?: throw NoSuchElementException("No session $sessionId was found")
        if (!session.state) {
            throw IllegalArgumentException("Cannot add player to closed session")
        }
        if (user in session.players) {
            throw IllegalArgumentException("User is already in session")
        }
        db.gamingSessions.addPlayer(sessionId, user.id)
        return@withAuthorization user.id
    }

    fun removePlayerFromSession(
        sessionId: Int,
        token: UUID,
        playerId: Int,
    ) = withAuthorization(token) { user ->
        val session = db.gamingSessions.get(sessionId)
            ?: throw NoSuchElementException("No session $sessionId was found")
        if (session.players.none { it.id == playerId }) {
            throw IllegalArgumentException("Player is not in session")
        }
        if (session.hostId != user.id && session.players.none { it.id == user.id }) {
            throw ForbiddenException("Player can only be removed by the host or the player itself")
        }
        if (!session.state) {
            throw IllegalArgumentException("Cannot remove player from closed session")
        }
        db.gamingSessions.removePlayer(sessionId, playerId)
    }
}
