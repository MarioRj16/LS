package pt.isel.ls.data.mem

import kotlinx.datetime.LocalDateTime
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
        require(capacity >= 1) { "The session capacity has to be at least 1" }
        require(!date.isPast()) {
            "The session starting date cannot precede the current LocalDateTime"
        }
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
        game: Int,
        date: LocalDateTime?,
        isOpen: Boolean?,
        player: Int?,
        limit: Int,
        skip: Int,
    ): List<GamingSession> {
        games.table[game] ?: throw NoSuchElementException("No game with id $game was found")
        var sessions = gamingSessions.table.filter { (_, value) -> value.gameId == game }
        if (sessions.isEmpty()) {
            return sessions.values.toList()
        }

        if (player is Int) {
            sessions = sessions.filter { (_, value) -> value.players.find { it.id == player } is Player }
        }

        if (date is LocalDateTime) {
            return sessions.values.filter { it.startingDate == date }
        }

        return sessions.values.toList().paginate(skip, limit)
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

    override fun isOwner(
        sessionId: Int,
        playerId: Int,
    ): Boolean {
        val session =
            gamingSessions.table[sessionId]
                ?: throw NoSuchElementException("Session $sessionId does not exist")
        return session.creatorId == playerId
    }
}
