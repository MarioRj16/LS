package pt.isel.ls.utils.factories

import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random

class GamingSessionFactory(private val gamingSessions: GamingSessionsData) {
    fun createRandomGamingSession(
        gameId: Int,
        playerId: Int,
        players: Set<Player> = emptySet<Player>(),
    ): Session {
        val randomCapacity =
            if (players.size >= 2) Random.nextInt(players.size, 33) else Random.nextInt(2, 33)
        val session = gamingSessions.create(randomCapacity, gameId, plusDaysToCurrentDateTime(), playerId)
        if (players.isEmpty()) {
            return session
        }
        players.forEach {
            gamingSessions.addPlayer(session.id, it.id)
        }
        return gamingSessions.get(session.id)!!
    }
}
