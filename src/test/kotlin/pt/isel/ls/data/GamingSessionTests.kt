package pt.isel.ls.data

import kotlinx.datetime.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.utils.tomorrowLocalDateTime
import pt.isel.ls.utils.yesterdayLocalDateTime
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionTests : AbstractDataTests() {

    @Test
    fun `create() returns gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val capacity = 2
        val date = LocalDateTime(
            LocalDate(2050, 3, 3),
            LocalTime(1, 1, 1, 1)
        )
        val session = gamingSessions.create(capacity, game.id, date)

        assertTrue(session.id == 1)
        assertEquals(game.id, session.gameId)
        assertEquals(capacity, session.maxCapacity)
        assertEquals(date, session.startingDate)
        assertTrue(session.players.isEmpty())
        assertTrue(session.state)
    }

    @Test
    fun `create() throws exception for non existing game`() {
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(1, 1, tomorrowLocalDateTime())
        }
    }

    @Test
    fun `create() throws exception for invalid capacity`() {
        val game = gameFactory.createRandomGame()
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(-1, game.id, tomorrowLocalDateTime())
        }
    }

    @Test
    fun `create() throws exception for past starting date`() {
        val game = gameFactory.createRandomGame()
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(1, game.id, yesterdayLocalDateTime())
        }
    }

    @Test
    fun `get() returns gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)

        assertEquals(session, gamingSessions.get(session.id))
    }

    @Test
    fun `get() throws exception for non existing gaming session`() {
        assertThrows<NoSuchElementException> {
            gamingSessions.get(1)
        }
    }

    @Test
    fun `addPlayer() adds player to gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)
        val player = playerFactory.createRandomPlayer()

        gamingSessions.addPlayer(session.id, player.id)

        val playersInSession = gamingSessions.get(session.id).players

        assertTrue(player in playersInSession)
    }

    @Test
    fun `addPlayer() throws exception for non existing gaming session`() {
        assertThrows<NoSuchElementException> {
            val player = playerFactory.createRandomPlayer()
            gamingSessions.addPlayer(1, player.id)
        }
    }

    @Test
    fun `addPlayer() throws exception if gaming session is already at max capacity`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)

        repeat(session.maxCapacity) {
            val player = playerFactory.createRandomPlayer()
            gamingSessions.addPlayer(session.id, player.id)
        }

        assertThrows<IllegalArgumentException> {
            val player = playerFactory.createRandomPlayer()
            gamingSessions.addPlayer(session.id, player.id)
        }
    }

    @Test
    fun `addPlayer() throws exception for session past starting date`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val secondsToAdd = 1L
        val date = LocalDateTime(
            java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalTime.now().plusSeconds(secondsToAdd).toKotlinLocalTime()
        )
        val session = gamingSessions.create(2, game.id, date)
        assertThrows<IllegalArgumentException> {
            val msToAdd = (secondsToAdd * 1000)
            Thread.sleep(msToAdd)
            gamingSessions.addPlayer(session.id, player.id)
        }
    }

    @Test
    fun `search() returns gaming sessions successfully`() {
        val game = gameFactory.createRandomGame()
        val game2 = gameFactory.createRandomGame()

        assertTrue(
            gamingSessions.search(game.id, null, null, null, DEFAULT_LIMIT, DEFAULT_SKIP).isEmpty()
        )

        val session = gamingSessionFactory.createRandomGamingSession(game.id)
        val session2 = gamingSessionFactory.createRandomGamingSession(game.id)
        val session3 = gamingSessionFactory.createRandomGamingSession(game2.id)

        var searchResults =
            gamingSessions.search(game.id, null, null, null, DEFAULT_LIMIT, DEFAULT_SKIP)
        assertTrue(searchResults.size == 2)
        assertContains(searchResults, session)
        assertContains(searchResults, session2)

        searchResults =
            gamingSessions.search(game2.id, null, null, null, 1, 0)

        assertTrue(searchResults.size == 1)
        assertContains(searchResults, session3)
    }

    @Test
    fun `search() throws exception for non existing game`() {
        val game = gameFactory.createRandomGame()
        gamingSessionFactory.createRandomGamingSession(game.id)
        assertThrows<NoSuchElementException> {
            gamingSessions.search(10, null, null, null, DEFAULT_LIMIT, DEFAULT_SKIP)
        }
    }
}