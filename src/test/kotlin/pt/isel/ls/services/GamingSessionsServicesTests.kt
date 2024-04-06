package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import java.util.UUID
import kotlin.random.Random
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionsServicesTests : SessionServices(DataMem()) {
    private lateinit var token: UUID
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(db.players)
    private val gamingSessionFactory = GamingSessionFactory(db.gamingSessions)
    private val gameFactory = GameFactory(db.games)

    @BeforeEach
    fun setUp() {
        db.reset()
        user = playerFactory.createRandomPlayer()
        token = user.token
    }

    @Test
    fun `createSession() returns sessionResponse successfully`() {
        val game = gameFactory.createRandomGame()
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime(1L)
        val sessionCreate = SessionCreate(game.id, capacity, startingDate)
        val sessionResponse = createSession(sessionCreate, token)
        val expectedId = 1
        assertEquals(expectedId, sessionResponse.id)
    }

    @Test
    fun `getSession() returns gaming session successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val returnedSession = getSession(session.id, token)
        assertEquals(session, returnedSession)
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
        val returnedSession = updateSession(session.id, sessionUpdate, token)
        assertEquals(returnedSession, getSession(session.id, token))
    }

    @Test
    fun `updateSession() throws exception for non owner token`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val capacity = Random.nextInt(2, session.maxCapacity)
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600))
        assertThrows<ForbiddenException> {
            updateSession(session.id, sessionUpdate, token)
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
            updateSession(session.id, sessionUpdate, token)
        }
    }

    @Test
    fun `deleteSession() deletes gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        deleteSession(session.id, token)
    }

    @Test
    fun `deleteSession() throws exception for non owner token`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        assertThrows<ForbiddenException> {
            deleteSession(session.id, token)
        }
    }

    @Test
    fun `searchSessions() returns gaming sessions successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session1 = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val session2 = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val searchParameters = SessionSearch(game.id)

        val gamingSessions = searchSessions(searchParameters, token, DEFAULT_SKIP, DEFAULT_LIMIT)

        assertTrue(gamingSessions.size == 2)
        assertContains(gamingSessions, session1)
        assertContains(gamingSessions, session2)
    }

    @Test
    fun `addPlayerToSession() returns id of added player successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)
        val returnedPlayerId = addPlayerToSession(session.id, token)
        assertEquals(user.id, returnedPlayerId)
    }

    @Test
    fun `removePlayerFromSession() removes player successfully`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id, setOf(player))
        removePlayerFromSession(session.id, token, player.id)
        val updatedSession = getSession(session.id, token)

        assertTrue(session.players.contains(player))
        assertTrue(session != updatedSession)
        assertTrue(updatedSession.players.isEmpty())
    }

    @Test
    fun `removePlayerFromSession() throws exception for non owner token`() {
        val host = playerFactory.createRandomPlayer()
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, host.id)

        assertThrows<ForbiddenException> {
            removePlayerFromSession(session.id, token, player.id)
        }
    }
}
