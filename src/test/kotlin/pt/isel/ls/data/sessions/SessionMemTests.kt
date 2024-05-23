package pt.isel.ls.data.sessions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.SESSION_MAX_CAPACITY
import pt.isel.ls.SESSION_MIN_CAPACITY
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.DataMemTests
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import pt.isel.ls.utils.toLong
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SessionMemTests : DataMemTests(), SessionTests {

    @Test
    override fun createReturnsGamingSessionSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val capacity = 2
        val date =
            LocalDateTime(
                LocalDate(2050, 3, 3),
                LocalTime(1, 1, 1),
            )
        val sessionCreate = SessionCreate(game.id, capacity, date.toLong())
        val session = gamingSessions.create(sessionCreate, player.id)

        assertTrue(session.id == 1)
        assertEquals(game.id, session.gameId)
        assertEquals(capacity, session.maxCapacity)
        assertEquals(date, session.startingDate)
        assertTrue(session.players.isEmpty())
        assertTrue(session.state)
    }

    @Test
    override fun getReturnsGamingSessionSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        assertEquals(session, gamingSessions.get(session.id))
    }

    @Test
    override fun getReturnsNullForNonExistingGamingSession() {
        assertNull(gamingSessions.get(1))
    }

    @Test
    override fun addPlayerAddsPlayerToGamingSessionSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        gamingSessions.addPlayer(session.id, player.id)

        val playersInSession = gamingSessions.get(session.id)!!.players

        assertTrue(player in playersInSession)
    }

    @Test
    override fun removePlayerRemovesPlayerFromSessionSuccessfully() {
        val game = gameFactory.createRandomGame()
        val creator = playerFactory.createRandomPlayer()
        val player = playerFactory.createRandomPlayer()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, creator.id, setOf(player))

        gamingSessions.removePlayer(session.id, player.id)

        val expected = gamingSessions.get(session.id)!!.players
        assertTrue(session.players.contains(player))
        assertTrue(expected.isEmpty())
    }

    @Test
    override fun updateUpdatesGamingSessionSuccessfully() {
        val session = gamingSessionFactory.createRandomGamingSession()
        val newDate = plusDaysToCurrentDateTime()
        val newCapacity: Int =
            Random.nextInt(maxOf(SESSION_MIN_CAPACITY, session.maxCapacity), SESSION_MAX_CAPACITY + 1)
        val expected = session.copy(startingDate = newDate, maxCapacity = newCapacity)
        val sessionUpdate = SessionUpdate(newCapacity, newDate.toLong())
        gamingSessions.update(session.id, sessionUpdate)
        val result = gamingSessions.get(session.id)
        assertEquals(expected.id, result!!.id)
        assertEquals(expected.gameId, result.gameId)
        assertEquals(expected.maxCapacity, result.maxCapacity)
        assertEquals(expected.startingDate.toLong(), result.startingDate.toLong())
        assertEquals(expected.players, result.players)
    }

    @Test
    override fun deleteDeletesGamingSessionSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        assertEquals(session, gamingSessions.get(session.id))
        gamingSessions.delete(session.id)
        assertTrue(gamingSessions.get(session.id) == null)
    }

    @Test
    override fun isOwnerChecksIfPlayerIsOwnerSuccessfully() {
        val game = gameFactory.createRandomGame()
        val player1 = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session1 = gamingSessionFactory.createRandomGamingSession(game.id, player1.id)

        assertTrue(gamingSessions.isOwner(session1.id, player1.id))
        assertFalse(gamingSessions.isOwner(session1.id, player2.id))
    }

    @Test
    override fun searchByGameReturnsGamingSessionsSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val game2 = gameFactory.createRandomGame()
        val searchParameters1 = SessionSearch(game.id, hostId = null)
        var searchResults = gamingSessions.search(searchParameters1, DEFAULT_LIMIT, DEFAULT_SKIP)
        assertEquals(0, searchResults.element.size)

        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val session2 = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val session3 = gamingSessionFactory.createRandomGamingSession(game2.id, player.id)

        searchResults = gamingSessions.search(SessionSearch(game.id, hostId = null), DEFAULT_LIMIT, DEFAULT_SKIP)
        assertEquals(2, searchResults.element.size)
        assertTrue {
            searchResults.element.all { it.id == session.id || it.id == session2.id }
        }

        searchResults = gamingSessions.search(SessionSearch(game2.id, hostId = null), DEFAULT_LIMIT, DEFAULT_SKIP)
        assertEquals(1, searchResults.element.size)
        assertTrue { searchResults.element.all { it.id == session3.id } }
    }

    @Test
    override fun searchByPlayerEmailReturnsGamingSessionsSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()

        val session = gamingSessionFactory.createRandomGamingSession(players = setOf(player))
        gamingSessionFactory.createRandomGamingSession(players = setOf(player2))

        val searchResults =
            gamingSessions.search(SessionSearch(playerName = player.name), DEFAULT_LIMIT, DEFAULT_SKIP)
        assertEquals(1, searchResults.element.size)
        assertTrue { searchResults.element.all { it.id == session.id } }
    }

    @Test
    override fun searchByDateReturnsGamingSessionsSuccessfully() {
        val session = gamingSessionFactory.createRandomGamingSession()
        val date = session.startingDate
        val searchResults =
            gamingSessions.search(SessionSearch(date = date, hostId = null), DEFAULT_LIMIT, DEFAULT_SKIP)
        assertEquals(1, searchResults.element.size)
        assertTrue { searchResults.element.all { it.id == session.id } }
    }

    @Test
    override fun searchByStateReturnsGamingSessionsSuccessfully() {
        val session = gamingSessionFactory.createRandomGamingSession()
        gamingSessionFactory.createRandomGamingSession(isOpen = !session.state)
        val searchResults =
            gamingSessions.search(SessionSearch(state = session.state, hostId = null), DEFAULT_LIMIT, DEFAULT_SKIP)
        assertEquals(1, searchResults.element.size)
        assertTrue { searchResults.element.all { it.id == session.id } }
    }
}
