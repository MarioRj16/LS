package pt.isel.ls.domain

import org.junit.jupiter.api.Test
import pt.isel.ls.utils.*
import pt.isel.ls.utils.generateRandomString
import java.util.*
import kotlin.random.Random
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GamingSessionsTests {

    private val sessionId = 1
    private val gameId = 1
    private val maxCapacity = 2
    private val emptySetOfPlayers = setOf<Player>()

    private val maxCapacitySetOfPlayers = mutableSetOf<Player>()
    init {
        repeat(maxCapacity){
            maxCapacitySetOfPlayers += Player(
                Random.nextInt(1, maxCapacity),
                generateRandomString(),
                generateRandomEmail(),
                UUID.randomUUID()
            )
        }
    /**
     * There is the chance of having players with the same ID, that is not relevant since we are not testing the
     * player here
     */
    }

    @Test
    fun `gaming session state is true when starting date is in the future and players are not at max capacity`(){
        val session = GamingSession(sessionId, gameId, maxCapacity, tomorrowLocalDateTime(), emptySetOfPlayers)
        assertTrue(session.state)
    }

    @Test
    fun `gaming session state is false when starting date is in the past`(){
        val session = GamingSession(sessionId, gameId, maxCapacity, yesterdayLocalDateTime(), emptySetOfPlayers)
        assertFalse(session.state)
    }

    @Test
    fun `gaming session state is false when players are at max capacity`(){
        val session = GamingSession(sessionId, gameId, maxCapacity, yesterdayLocalDateTime(), maxCapacitySetOfPlayers)
        assertFalse(session.state)
    }

    @Test
    fun `gaming session state is false when players are at max capacity and starting date is in the past`(){
        val session = GamingSession(sessionId, gameId, maxCapacity, yesterdayLocalDateTime(), maxCapacitySetOfPlayers)
        assertFalse(session.state)
    }
}