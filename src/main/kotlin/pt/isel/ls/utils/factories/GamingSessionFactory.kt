package pt.isel.ls.utils.factories

import pt.isel.ls.data.GamingSessionStorage
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.utils.tomorrowLocalDateTime
import kotlin.random.Random

class GamingSessionFactory(private val gamingSessions: GamingSessionStorage) {

    fun createRandomGamingSession(gameId: Int): GamingSession {
        val randomCapacity = Random.nextInt(2, 129)
        return gamingSessions.create(randomCapacity, gameId, tomorrowLocalDateTime())
    }
}