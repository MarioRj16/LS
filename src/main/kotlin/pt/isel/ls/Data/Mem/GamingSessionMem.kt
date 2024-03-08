package pt.isel.ls.Data.Mem

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Domain.GamingSession
import pt.isel.ls.Domain.Player
import pt.isel.ls.utils.currentLocalDateTime

class GamingSessionMem(val schema: DataMemSchema): GamingSessionStorage {
    override fun create(capacity: Int, game: Int, date: LocalDateTime): GamingSession {
        require(capacity >= 1){"The session capacity has to be at least 1"}
        require(date >= currentLocalDateTime()){
            "The session starting date cannot precede the current LocalDateTime"
        }
        require(schema.gamesDB.containsKey(game)){"The provided game does not exist"}
        val obj = GamingSession(
            id = schema.gamingSessionsNextId,
            game = game,
            capacity = capacity,
            startingDate = date,
            players = emptySet<Player>()
        )
        schema.gamingSessionsDB[schema.gamingSessionsNextId] = obj
        return obj
    }

    override fun get(sessionId: Int): GamingSession? {
        return schema.gamingSessionsDB[sessionId]
    }

    override fun search(game: Int, date: LocalDateTime?, isOpen: Boolean?, player: Int?): List<GamingSession> {
        var sessions = schema.gamingSessionsDB.filter { (_, value) -> value.game == game }
        if(player is Int)
            sessions = sessions.filter { (_, value) -> value.players.find { it.id == player } is Player }

        if(date is LocalDateTime)
            return sessions.values.filter { it.startingDate == date }

        return sessions.values.toList()
    }


    override fun addPlayer(session: Int, player: Int): Boolean {
        //TODO: Ask if we should be returning a boolean here
        schema.gamingSessionsDB[session] ?: throw NoSuchElementException("Session $session does not exist")
        require(schema.gamingSessionsDB[session]!!.players.size < schema.gamingSessionsDB[session]!!.capacity){
            "The session $session is already at maximum capacity"
        }
        val playerToAdd = schema.playersDB[player]
        playerToAdd ?: throw NoSuchElementException("Player $player does not exist")
        if(playerToAdd in schema.gamingSessionsDB[session]!!.players)
            return false
        schema.gamingSessionsDB[session] =
            schema.gamingSessionsDB[session]!!.copy(players = (schema.gamingSessionsDB[session]!!.players + playerToAdd))
        return true
    }
}