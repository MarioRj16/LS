package pt.isel.ls.utils

import pt.isel.ls.data.GamingSessionStorage
import pt.isel.ls.domain.GamingSession
import kotlin.random.Random

class GamingSessionFactory(private val gamingSessions: GamingSessionStorage) {

    fun createRandomGamingSession(gameId: Int): GamingSession {
        val randomCapacity = Random.nextInt(1, 129)
        return gamingSessions.create(randomCapacity, gameId, tomorrowLocalDateTime())
    }
}