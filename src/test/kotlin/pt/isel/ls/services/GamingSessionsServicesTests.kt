package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.api.models.SessionUpdate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.minusDaysToCurrentDateTime
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionsServicesTests : SessionServices(DataMem()) {
    private lateinit var bearerToken: String
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(db.players)
    private val gamingSessionFactory = GamingSessionFactory(db.gamingSessions)
    private val gameFactory = GameFactory(db.games)

    @BeforeEach
    fun setUp() {
        db.reset()
        user = playerFactory.createRandomPlayer()
        bearerToken = "Bearer ${user.token}"
    }

    @Test
    fun `createSession() returns sessionResponse successfully`() {
        val game = gameFactory.createRandomGame()
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime(1L)
        val sessionCreate = SessionCreate(game.id, capacity, startingDate)
        val sessionResponse = createSession(sessionCreate, bearerToken)
        val expectedId = 1
        assertEquals(expectedId, sessionResponse.id)
    }

    @Test
    fun `getSession() returns gaming session successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val returnedSession = getSession(session.id, bearerToken)
        assertEquals(session, returnedSession)
    }

    @Test
    fun `getSession() throws exception for null id`() {
        assertThrows<IllegalArgumentException> {
            getSession(null, bearerToken)
        }
    }

    @Test
    fun `updateSession() updates gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        val capacity =
            if (session.maxCapacity > 3) {
                Random.nextInt(2, session.maxCapacity)
            } else {
                Random.nextInt(session.maxCapacity, 10)
            }
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600))
        val returnedSession = updateSession(session.id, sessionUpdate, bearerToken)
        assertEquals(returnedSession, getSession(session.id, bearerToken))
    }

    @Test
    fun `updateSession() throws exception for null id`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        val capacity = Random.nextInt(2, session.maxCapacity)
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600))
        assertThrows<IllegalArgumentException> {
            updateSession(null, sessionUpdate, bearerToken)
        }
    }

    @Test
    fun `updateSession() throws exception for non owner token`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val capacity = Random.nextInt(2, session.maxCapacity)
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600))
        assertThrows<ForbiddenException> {
            updateSession(session.id, sessionUpdate, bearerToken)
        }
    }

    @Test
    fun `updateSession() throws exception for capacity lower than number of players in gaming session`() {
        val players = List(10) {
            playerFactory.createRandomPlayer()
        }.toSet()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id, players)
        val capacity = Random.nextInt(2, players.size)
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600))
        assertThrows<IllegalArgumentException> {
            updateSession(session.id, sessionUpdate, bearerToken)
        }
    }

    @Test
    fun `update() throws exception for past date`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        val capacity = Random.nextInt(2, session.maxCapacity)
        val sessionUpdate = SessionUpdate(capacity, minusDaysToCurrentDateTime(600))
        assertThrows<IllegalArgumentException> {
            updateSession(session.id, sessionUpdate, bearerToken)
        }
    }

    @Test
    fun `deleteSession() deletes gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        deleteSession(session.id, bearerToken)
    }

    @Test
    fun `deleteSession() throws exception for null id`() {
        assertThrows<IllegalArgumentException> {
            deleteSession(null, bearerToken)
        }

        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        assertThrows<IllegalArgumentException> {
            deleteSession(null, bearerToken)
        }
    }

    @Test
    fun `deleteSession() throws exception for non owner token`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        assertThrows<ForbiddenException> {
            deleteSession(session.id, bearerToken)
        }
    }

    @Test
    fun `searchSessions() returns gaming sessions successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session1 = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val session2 = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val searchParameters = SessionSearch(game.id)

        val gamingSessions = searchSessions(searchParameters, bearerToken, DEFAULT_SKIP, DEFAULT_LIMIT)

        assertTrue(gamingSessions.size == 2)
        assertContains(gamingSessions, session1)
        assertContains(gamingSessions, session2)
    }

    @Test
    fun `addPlayerToSession() returns id of added player successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val returnedPlayerId = addPlayerToSession(session.id, bearerToken)
        assertEquals(user.id, returnedPlayerId)
    }

    @Test
    fun `addPlayerToSession() throws exception for null id`() {
        assertThrows<IllegalArgumentException> {
            addPlayerToSession(null, bearerToken)
        }
    }

    @Test
    fun `removePlayerFromSession() removes player successfully`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id, setOf(player))
        removePlayerFromSession(session.id, bearerToken, player.id)
        val updatedSession = getSession(session.id, bearerToken)

        assertTrue(session.players.contains(player))
        assertTrue(session != updatedSession)
        assertTrue(updatedSession.players.isEmpty())
    }

    @Test
    fun `removePlayerFromSession() throws exception for null player id`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        assertThrows<IllegalArgumentException> {
            removePlayerFromSession(session.id, bearerToken, null)
        }
    }

    @Test
    fun `removePlayerFromSession() throws exception for null session id`() {
        val host = playerFactory.createRandomPlayer()
        assertThrows<IllegalArgumentException> {
            removePlayerFromSession(null, bearerToken, host.id)
        }
    }

    @Test
    fun `removePlayerFromSession() throws exception for non owner token`() {
        val host = playerFactory.createRandomPlayer()
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, host.id)

        assertThrows<ForbiddenException> {
            removePlayerFromSession(session.id, bearerToken, player.id)
        }
    }
}
