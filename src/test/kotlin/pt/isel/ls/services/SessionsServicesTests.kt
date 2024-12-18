package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.SESSION_MAX_CAPACITY
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionDetails
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import pt.isel.ls.utils.toLong
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SessionsServicesTests : SessionsServices(DataMem()) {
    private lateinit var token: UUID
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(db.players)
    private val gamingSessionFactory = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)
    private val gameFactory = GameFactory(db.games, db.genres)

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
        val sessionCreate = SessionCreate(game.id, capacity, startingDate.toLong())
        val sessionResponse = createSession(sessionCreate, token)
        val expectedId = 1
        assertEquals(expectedId, sessionResponse.id)
    }

    @Test
    fun `createSession() throws exception for non existing game`() {
        val game = gameFactory.createRandomGame()
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime(1L)
        val sessionCreate = SessionCreate(game.id + 1, capacity, startingDate.toLong())
        assertThrows<IllegalArgumentException> {
            createSession(sessionCreate, token)
        }
    }

    @Test
    fun `getSession() returns gaming session successfully`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val expected = SessionDetails(gamingSessionFactory.createRandomGamingSession(game.id, player.id), game)
        val returnedSession = getSession(expected.id, token)
        assertEquals(expected, returnedSession)
    }

    @Test
    fun `getSession() throws exception for non existing gaming session`() {
        assertThrows<NoSuchElementException> {
            getSession(1, token)
        }
    }

    @Test
    fun `updateSession() updates gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = SessionDetails(
            gamingSessionFactory.createRandomGamingSession(game.id, user.id),
            game,
        )
        val capacity =
            if (session.capacity > 3) {
                Random.nextInt(2, session.capacity)
            } else {
                Random.nextInt(session.capacity, 10)
            }

        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600).toLong())
        updateSession(session.id, sessionUpdate, token)

        val updatedSession = getSession(session.id, token)

        assertTrue(updatedSession != session)
        assertEquals(updatedSession.id, session.id)
        assertEquals(updatedSession.game, session.game)
        assertEquals(updatedSession.host, session.host)
        assertEquals(updatedSession.capacity, sessionUpdate.capacity)
        assertEquals(updatedSession.date.toLong(), sessionUpdate.startingDate)
    }

    @Test
    fun `updateSession() throws exception for capacity lower than number of players in gaming session`() {
        val players = List(10) {
            playerFactory.createRandomPlayer()
        }.toSet()
        val game = gameFactory.createRandomGame()
        val session = run {
            var session = gamingSessionFactory.createRandomGamingSession(game.id, user.id, players)
            while (session.maxCapacity == players.size) {
                session = gamingSessionFactory.createRandomGamingSession(game.id, user.id, players)
            }
            return@run session
        }
        val capacity = Random.nextInt(2, players.size)
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600).toLong())
        assertThrows<IllegalArgumentException> {
            updateSession(session.id, sessionUpdate, token)
        }
    }

    @Test
    fun `updateSession() throws exception for closed session`() {
        val game = gameFactory.createRandomGame()
        val playersInSession = List(10) {
            playerFactory.createRandomPlayer()
        }.toSet()
        val session = gamingSessionFactory.createRandomGamingSession(
            gameId = game.id,
            hostId = user.id,
            isOpen = false,
            players = playersInSession,
        )

        val capacity =
            if (session.maxCapacity == SESSION_MAX_CAPACITY) {
                SESSION_MAX_CAPACITY - 1
            } else {
                SESSION_MAX_CAPACITY
            }
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600).toLong())
        assertThrows<IllegalArgumentException> {
            updateSession(session.id, sessionUpdate, token)
        }
    }

    @Test
    fun `updateSession() throws exception for non host user`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        val capacity =
            if (session.maxCapacity > 3) {
                Random.nextInt(2, session.maxCapacity)
            } else {
                Random.nextInt(session.maxCapacity, 10)
            }
        val sessionUpdate = SessionUpdate(capacity, plusDaysToCurrentDateTime(600).toLong())
        assertThrows<ForbiddenException> {
            updateSession(session.id, sessionUpdate, playerFactory.createRandomPlayer().token)
        }
    }

    @Test
    fun `deleteSession() deletes gaming session successfully`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        deleteSession(session.id, token)
    }

    @Test
    fun `deleteSession() throws exception for non existing gaming session`() {
        assertThrows<NoSuchElementException> {
            deleteSession(1, token)
        }
    }

    @Test
    fun `deleteSession() throws exception for non host user`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        assertThrows<ForbiddenException> {
            deleteSession(session.id, playerFactory.createRandomPlayer().token)
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

        assertTrue(gamingSessions.total == 2)
        assertTrue { gamingSessions.sessions.all { it.id == session1.id || it.id == session2.id } }
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
    fun `addPlayerToSession() throws exception for closed session`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(
            gameId = game.id,
            hostId = user.id,
            players = setOf(),
        )
        repeat(session.maxCapacity) {
            val player = playerFactory.createRandomPlayer()
            db.gamingSessions.addPlayer(session.id, player.id)
        }
        assertThrows<IllegalArgumentException> {
            addPlayerToSession(session.id, token)
        }
    }

    @Test
    fun `addPlayerToSession() throws exception for user already in session`() {
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id)
        addPlayerToSession(session.id, token)
        assertThrows<IllegalArgumentException> {
            addPlayerToSession(session.id, token)
        }
    }

    @Test
    fun `addPlayerToSession() throws exception non existing gaming session`() {
        assertThrows<NoSuchElementException> {
            addPlayerToSession(1, token)
        }
    }

    @Test
    fun `removePlayerFromSession() removes player successfully if user is host`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session = SessionDetails(
            gamingSessionFactory.createRandomGamingSession(gameId = game.id, hostId = user.id, players = setOf(player)),
            game,
        )
        removePlayerFromSession(session.id, token, player.id)
        val updatedSession = getSession(session.id, token)

        assertTrue(session.players.contains(PlayerDetails(player)))
        assertTrue(session != updatedSession)
        assertTrue(updatedSession.players.isEmpty())
    }

    @Test
    fun `removePlayerFromSession() removes player successfully if user is trying to remove himself`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session = SessionDetails(
            gamingSessionFactory.createRandomGamingSession(
                game.id,
                user.id,
                setOf(player),
            ),
            game,
        )
        removePlayerFromSession(session.id, player.token, player.id)
        val updatedSession = getSession(session.id, token)

        assertTrue(session.players.contains(PlayerDetails(player)))
        assertTrue(session != updatedSession)
        assertTrue(updatedSession.players.isEmpty())
    }

    @Test
    fun `removePlayerFromSession() throws exception for non existing gaming session`() {
        assertThrows<NoSuchElementException> {
            removePlayerFromSession(1, token, 1)
        }
    }

    @Test
    fun `removePlayerFromSession() throws exception for non authorized user`() {
        val host = user
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session = gamingSessionFactory.createRandomGamingSession(
            gameId = game.id,
            hostId = host.id,
            setOf(player2),
        )
        assertThrows<ForbiddenException> {
            removePlayerFromSession(session.id, player.token, player2.id)
        }
    }

    @Test
    fun `removePlayerFromSession() throws exception for closed session`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session = gamingSessionFactory.createRandomGamingSession(game.id, user.id, setOf(player))
        repeat(session.maxCapacity - 1) {
            db.gamingSessions.addPlayer(session.id, playerFactory.createRandomPlayer().id)
        }

        assertThrows<IllegalArgumentException> {
            removePlayerFromSession(session.id, token, player.id)
        }
    }
}
