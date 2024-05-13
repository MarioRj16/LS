package pt.isel.ls.data.mem

import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionListResponse
import pt.isel.ls.api.models.sessions.SessionResponse
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.SessionsData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.PaginateResponse

class SessionsMem(
    private val sessions: DataMemTable<Session> = DataMemTable(),
    private val games: DataMemTable<Game> = DataMemTable(),
    private val players: DataMemTable<Player> = DataMemTable(),
) : SessionsData {

    override fun create(
        sessionInput: SessionCreate,
        hostId: Int,
    ): Session {
        val (game, capacity, _) = sessionInput
        val date = sessionInput.startingDateFormatted
        val obj =
            Session(
                sessions.nextId.get(),
                game,
                hostId,
                capacity,
                date,
                emptySet(),
            )
        sessions.table[sessions.nextId.get()] = obj
        return obj
    }

    override fun get(sessionId: Int): Session? = sessions.table[sessionId]

    override fun search(
        sessionParameters: SessionSearch,
        limit: Int,
        skip: Int,
    ): SessionListResponse {
        val (game, date, state, player, hostId) = sessionParameters
        var sessions: List<Session> = sessions.table.values.toList()

        if (game != null) {
            sessions = sessions.filter { it.gameId == game }
        }
        if (player != null) {
            sessions = sessions.filter {
                    session ->
                session.players.any { p -> p.name == player }
            }
        }
        if (hostId != null) {
            sessions = sessions.filter { it.hostId == hostId }
        }

        if (state != null) {
            sessions = sessions.filter { it.state == state }
        }
        if (date != null) {
            sessions = sessions.filter { it.startingDate >= date }
        }

        val list = sessions.map { session -> SessionResponse(session, games.table[session.gameId]!!)}

        return SessionListResponse(PaginateResponse.fromList(list, skip, limit))
    }

    override fun update(sessionId: Int, sessionUpdate: SessionUpdate) {
        val capacity = sessionUpdate.capacity
        val startingDate = sessionUpdate.startingDateFormatted
        val session =
            sessions.table[sessionId]!!.copy(maxCapacity = capacity, startingDate = startingDate)
        sessions.table[sessionId] = session
    }

    override fun delete(sessionId: Int) {
        sessions.table.remove(sessionId)!!
    }

    override fun addPlayer(
        session: Int,
        player: Int,
    ) {
        val playerToAdd = players.table[player]!!
        sessions.table[session] =
            sessions.table[session]!!.copy(players = (sessions.table[session]!!.players + playerToAdd))
    }

    override fun removePlayer(sessionId: Int, playerId: Int) {
        val session = sessions.table[sessionId]!!
        val playerToRemove = session.players.find { it.id == playerId }!!
        sessions.table[sessionId] =
            session.copy(players = (session.players - playerToRemove))
    }

    override fun isOwner(
        sessionId: Int,
        playerId: Int,
    ): Boolean {
        val session = sessions.table[sessionId]!!
        return session.hostId == playerId
    }
}
