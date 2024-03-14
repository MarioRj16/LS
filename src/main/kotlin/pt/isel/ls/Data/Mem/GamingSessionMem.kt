package pt.isel.ls.Data.Mem

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.GamingSession
import pt.isel.ls.Domain.Player
import pt.isel.ls.utils.currentLocalDateTime

class GamingSessionMem(
    private val gamingSessions: DBTableMem<GamingSession>,
    private val players: DBTableMem<Player>,
    private val games: DBTableMem<Game>
): GamingSessionStorage {
    override fun create(capacity: Int, game: Int, date: LocalDateTime): GamingSession {
        require(capacity >= 1){"The session capacity has to be at least 1"}
        require(date >= currentLocalDateTime()){
            "The session starting date cannot precede the current LocalDateTime"
        }
        require(games.table.containsKey(game)){"The provided game does not exist"}
        val obj = GamingSession(
            id = gamingSessions.nextId,
            game = game,
            capacity = capacity,
            startingDate = date,
            players = emptySet<Player>()
        )
        gamingSessions.table[gamingSessions.nextId] = obj
        return obj
    }

    override fun get(sessionId: Int): GamingSession? {
        return gamingSessions.table[sessionId]
    }

    override fun search(game: Int, date: LocalDateTime?, isOpen: Boolean?, player: Int?): List<GamingSession> {
        var sessions = gamingSessions.table.filter { (_, value) -> value.game == game }
        if(player is Int)
            sessions = sessions.filter { (_, value) -> value.players.find { it.id == player } is Player }

        if(date is LocalDateTime)
            return sessions.values.filter { it.startingDate == date }

        return sessions.values.toList()
    }


    override fun addPlayer(session: Int, player: Int): Boolean {
        //TODO: Ask if we should be returning a boolean here
        gamingSessions.table[session] ?: throw NoSuchElementException("Session $session does not exist")
        require(gamingSessions.table[session]!!.players.size < gamingSessions.table[session]!!.capacity){
            "The session $session is already at maximum capacity"
        }
        val playerToAdd = players.table[player]
        playerToAdd ?: throw NoSuchElementException("Player $player does not exist")
        if(playerToAdd in gamingSessions.table[session]!!.players)
            return false
       gamingSessions.table[session] =
            gamingSessions.table[session]!!.copy(players = (gamingSessions.table[session]!!.players + playerToAdd))
        return true
    }
}