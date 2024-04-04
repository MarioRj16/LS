package pt.isel.ls.utils.factories

import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random

class GamingSessionFactory(private val gamingSessions: GamingSessionsData) {
    fun createRandomGamingSession(
        gameId: Int,
        playerId: Int,
        players: Set<Player> = emptySet<Player>(),
    ): GamingSession {
        val randomCapacity =
            if(players.isNotEmpty()) Random.nextInt(players.size, 33) else Random.nextInt(2, 33)
        val session = gamingSessions.create(randomCapacity, gameId, plusDaysToCurrentDateTime(), playerId)
        if (players.isEmpty())
            return session
        players.forEach{
            gamingSessions.addPlayer(session.id, it.id)
        }
        return gamingSessions.get(session.id)
    }
}
