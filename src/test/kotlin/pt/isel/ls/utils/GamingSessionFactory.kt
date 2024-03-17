package pt.isel.ls.utils

import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Player
import kotlin.random.Random

internal open class GamingSessionFactory(private val db: DataMem) {

    fun createRandomGamingSession(gameId: Int): GamingSession {
        val randomCapacity = Random.nextInt(1, 129)
        return db.gamingSessions.create(randomCapacity, gameId, currentLocalDateTime())
    }
}