package pt.isel.ls.data.mem

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.api.models.SessionUpdate
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.isPast
import pt.isel.ls.utils.paginate

class GamingSessionsMem(
    private val gamingSessions: DataMemTable<GamingSession> = DataMemTable(),
    private val players: DataMemTable<Player> = DataMemTable(),
    private val games: DataMemTable<Game> = DataMemTable(),
) : GamingSessionsData {
    override fun create(
        capacity: Int,
        game: Int,
        date: LocalDateTime,
        playerId: Int,
    ): GamingSession {
        require(games.table.containsKey(game)) { "The provided game does not exist" }
        val obj =
            GamingSession(
                gamingSessions.nextId.get(),
                game,
                playerId,
                capacity,
                date,
                emptySet(),
            )
        gamingSessions.table[gamingSessions.nextId.get()] = obj
        return obj
    }

    override fun get(sessionId: Int): GamingSession {
        return gamingSessions.table[sessionId]
            ?: throw NoSuchElementException("No gaming session with id $sessionId was found")
    }

    override fun search(
        sessionParameters: SessionSearch,
        limit: Int,
        skip: Int,
    ): List<GamingSession> {
        val (game, date, state, player) = sessionParameters
        games.table[game] ?: throw NoSuchElementException("No game with id $game was found")
        var sessions = gamingSessions.table.filter { (_, value) -> value.gameId == game }

        if (sessions.isEmpty()) {
            return sessions.values.toList()
        }

        if (player != null) {
            sessions = sessions.filter { (_, value) -> value.players.find { it.id == player } is Player }
        }

        if (state != null) {
            sessions = sessions.filter { (_, value) -> value.state == state }
        }

        if (date != null) {
            return sessions.values.filter { it.startingDate == date }
        }

        return sessions.values.toList().paginate(skip, limit)
    }

    override fun update(sessionId: Int, sessionUpdate: SessionUpdate): GamingSession {
        val (capacity, startingDate) = sessionUpdate
        val session =
            gamingSessions.table[sessionId]?.copy(maxCapacity = capacity, startingDate = startingDate)
                ?: throw NoSuchElementException("No gaming session with id $sessionId was found")

        gamingSessions.table[sessionId] = session
        return session
    }

    override fun delete(sessionId: Int) {
        gamingSessions.table.remove(sessionId)
            ?: throw NoSuchElementException("The provided gaming session does not exist")
    }

    override fun addPlayer(
        session: Int,
        player: Int,
    ) {
        gamingSessions.table[session] ?: throw NoSuchElementException("Session $session does not exist")
        require(gamingSessions.table[session]!!.state) {
            "Cannot add player for closed gaming session"
        }
        val playerToAdd = players.table[player]
        playerToAdd ?: throw NoSuchElementException("Player $player does not exist")
        if (playerToAdd in gamingSessions.table[session]!!.players) {
            throw ConflictException("Played could not be added to database")
        }
        gamingSessions.table[session] =
            gamingSessions.table[session]!!.copy(players = (gamingSessions.table[session]!!.players + playerToAdd))
    }

    override fun removePlayer(sessionId: Int, playerId: Int) {
        val session =
            gamingSessions.table[sessionId]
                ?: throw NoSuchElementException("Session $sessionId does not exist")
        require(!session.startingDate.isPast()) { "Changes cannot be made to past gaming sessions" }
        val player =
            session.players.find { it.id == playerId }
                ?: throw IllegalArgumentException("Player $playerId does not exist")
        gamingSessions.table[sessionId] =
            session.copy(players = (session.players - player))
    }

    override fun isOwner(
        sessionId: Int,
        playerId: Int,
    ): Boolean {
        val session =
            gamingSessions.table[sessionId]
                ?: throw NoSuchElementException("Session $sessionId does not exist")
        return session.hostId == playerId
    }
}
