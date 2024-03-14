package pt.isel.ls.utils

import pt.isel.ls.Data.Mem.DataMem
import pt.isel.ls.Domain.GamingSession
import pt.isel.ls.Domain.Player
import kotlin.random.Random

internal class GamingSessionFactory {
    companion object: DataMem(){
        fun createRandomGamingSession(gameId: Int, players: Set<Player>): GamingSession {
            val randomCapacity = Random.nextInt(1, 129)
            return gamingSessions.create(randomCapacity, gameId, currentLocalDateTime())
        }
    }
}