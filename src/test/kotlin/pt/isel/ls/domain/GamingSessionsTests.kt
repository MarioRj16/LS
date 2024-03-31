package pt.isel.ls.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.utils.*
import java.util.*
import kotlin.random.Random
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GamingSessionsTests {
    private val validSessionId = 1
    private val validGameId = 1
    private val validMaxCapacity = 2
    private val emptySetOfPlayers = setOf<Player>()
    private val validCreatorId = 1
    private val validStartingDate = plusDaysToCurrentDateTime(1)

    private val maxCapacitySetOfPlayers = mutableSetOf<Player>()

    init {
        repeat(validMaxCapacity) {
            maxCapacitySetOfPlayers +=
                Player(
                    Random.nextInt(1, validMaxCapacity),
                    generateRandomString(),
                    generateRandomEmail(),
                    UUID.randomUUID(),
                )
        }
        /**
         * There is the chance of having players with the same ID, that is not relevant since we are not testing the
         * player here
         */
    }

    @Test
    fun `gaming session state is true when starting date is in the future and players are not at max capacity`() {
        val session =
            GamingSession(
                validSessionId,
                validGameId,
                validCreatorId,
                validMaxCapacity,
                validStartingDate,
                emptySetOfPlayers,
            )
        assertTrue(session.state)
    }

    @Test
    fun `gaming session state is false when starting date is in the past`() {
        val timeToWait = 100L
        val session =
            GamingSession(
                validSessionId,
                validGameId,
                validCreatorId,
                validMaxCapacity,
                plusMillisecondsToCurrentDateTime(timeToWait),
                emptySetOfPlayers,
            )
        Thread.sleep(timeToWait + 1L)
        assertFalse(session.state)
    }

    @Test
    fun `gaming session state is false when players are at max capacity`() {
        val session =
            GamingSession(
                validSessionId,
                validGameId,
                validCreatorId,
                validMaxCapacity,
                validStartingDate,
                maxCapacitySetOfPlayers,
            )
        assertFalse(session.state)
    }

    @Test
    fun `gaming session state is false when players are at max capacity and starting date is in the past`() {
        val timeToWait = 100L
        val session =
            GamingSession(
                validSessionId,
                validGameId,
                validCreatorId,
                validMaxCapacity,
                plusMillisecondsToCurrentDateTime(timeToWait),
                maxCapacitySetOfPlayers,
            )
        Thread.sleep(timeToWait)
        assertFalse(session.state)
    }

    @Test
    fun `throws exception for non positive integer maxCapacity`() {
        assertThrows<IllegalArgumentException> {
            GamingSession(
                validSessionId,
                validGameId,
                validCreatorId,
                -1,
                minusDaysToCurrentDateTime(1),
                emptySetOfPlayers,
            )
        }
    }

    @Test
    fun `throws exception non positive game`() {
        assertThrows<IllegalArgumentException> {
            GamingSession(validSessionId, -1, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }

        assertThrows<IllegalArgumentException> {
            GamingSession(validSessionId, 0, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }
    }

    @Test
    fun `throws exception for non positive ID`() {
        assertThrows<IllegalArgumentException> {
            GamingSession(-1, validGameId, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }

        assertThrows<IllegalArgumentException> {
            GamingSession(0, validGameId, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }
    }
}
