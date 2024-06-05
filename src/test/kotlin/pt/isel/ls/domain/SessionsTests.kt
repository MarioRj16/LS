package pt.isel.ls.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomPassword
import pt.isel.ls.utils.generateRandomString
import pt.isel.ls.utils.minusDaysToCurrentDateTime
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import pt.isel.ls.utils.plusMillisecondsToCurrentDateTime
import java.util.*
import kotlin.random.Random
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SessionsTests {
    private val validSessionId = 1
    private val validGameId = 1
    private val validMaxCapacity = 2
    private val emptySetOfPlayers = setOf<Player>()
    private val validCreatorId = 1
    private val validStartingDate = plusDaysToCurrentDateTime(1)

    private val maxCapacitySetOfPlayers = mutableSetOf<Player>()

    private fun generateRandomPlayer() =
        Player(
            Random.nextInt(1, validMaxCapacity),
            generateRandomString(),
            generateRandomEmail(),
            generateRandomPassword().hash(),
            UUID.randomUUID(),
        )

    init {
        repeat(validMaxCapacity) {
            maxCapacitySetOfPlayers += generateRandomPlayer()
        }
        /**
         * There is the chance of having players with the same ID, that is not relevant since we are not testing the
         * player here
         */
    }

    @Test
    fun `gaming session state is true when starting date is in the future and players are not at max capacity`() {
        val session =
            Session(
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
            Session(
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
            Session(
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
            Session(
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
            Session(
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
            Session(validSessionId, -1, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }

        assertThrows<IllegalArgumentException> {
            Session(validSessionId, 0, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }
    }

    @Test
    fun `throws exception for non positive ID`() {
        assertThrows<IllegalArgumentException> {
            Session(-1, validGameId, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }

        assertThrows<IllegalArgumentException> {
            Session(0, validGameId, validCreatorId, validMaxCapacity, validStartingDate, emptySetOfPlayers)
        }
    }

    @Test
    fun `throws exception for maxCapacity lower than number of players in session`() {
        assertThrows<IllegalArgumentException> {
            Session(
                validSessionId,
                validGameId,
                validCreatorId,
                validMaxCapacity,
                validStartingDate,
                (maxCapacitySetOfPlayers + generateRandomPlayer()),
            )
        }
    }
}
