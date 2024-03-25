package pt.isel.ls.utils.factories

import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random

class GamingSessionFactory(private val gamingSessions: GamingSessionsData) {

    fun createRandomGamingSession(gameId: Int): GamingSession {
        val randomCapacity = Random.nextInt(2, 129)
        return gamingSessions.create(randomCapacity, gameId, plusDaysToCurrentDateTime(1L))
    }
}