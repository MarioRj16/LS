package pt.isel.ls.integration.sections
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.SESSION_MAX_CAPACITY
import pt.isel.ls.SESSION_MIN_CAPACITY
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionCreateResponse
import pt.isel.ls.api.models.sessions.SessionListResponse
import pt.isel.ls.api.models.sessions.SessionResponse
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.minusDaysToCurrentDateTime
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import pt.isel.ls.utils.plusMillisecondsToCurrentDateTime
import pt.isel.ls.utils.toLocalDateTime
import pt.isel.ls.utils.toLong
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SessionsTests : IntegrationTests() {

    @BeforeEach
    fun setUp() {
        db.reset()
    }

    @Test
    fun `createSession returns 201 for good response`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val requestBody = SessionCreate(game.id, 4, plusDaysToCurrentDateTime(1L).toLong())
        val request = Request(Method.POST, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<SessionCreateResponse>(bodyString()) }
            }
    }

    @Test
    fun `createSession returns 400 for non existing game`() {
        val player = playerFactory.createRandomPlayer()
        val requestBody = SessionCreate(999999, 4, plusDaysToCurrentDateTime().toLong())
        val request = Request(Method.POST, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createSession returns 400 for invalid capacity`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val requestBody = SessionCreate(game.id, SESSION_MAX_CAPACITY + 1, plusDaysToCurrentDateTime().toLong())
        val request = Request(Method.POST, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createSession returns 400 for past starting date`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val requestBody = SessionCreate(game.id, 4, minusDaysToCurrentDateTime(1L).toLong())
        val request = Request(Method.POST, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `addPlayerToSession returns 200 for good request`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id)
        val request = Request(Method.POST, "$URI_PREFIX/sessions/${session.id}")
            .json("")
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
            }
    }

    @Test
    fun `addPlayerToSession returns 404 for non-existing session`() {
        val player = playerFactory.createRandomPlayer()
        val request = Request(Method.POST, "$URI_PREFIX/sessions/99999")
            .json("")
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.NOT_FOUND, status)
            }
    }

    @Test
    fun `removePlayerFromSession (by host) returns 204 for good request`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player2))
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player2.id}")
            .json("")
            .token(player.token)

        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun `removePlayerFromSession (by player himself) returns 204 for good request`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player2))
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player2.id}")
            .json("")
            .token(player2.token)

        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 403 for unauthorized player`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player))
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player.id}")
            .json("")
            .token(player2.token)

        client(request).apply {
            assertEquals(Status.FORBIDDEN, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 404 for non-existing session`() {
        val player = playerFactory.createRandomPlayer()
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/99999/players/${player.id}")
            .json("")
            .token(player.token)

        client(request).apply {
            assertEquals(Status.NOT_FOUND, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 400 for player not is session`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = emptySet())
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player.id}")
            .json("")
            .token(player.token)

        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 404 for non-existing player`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player))
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/9999")
            .json("")
            .token(player.token)

        client(request).apply {
            assertEquals(Status.NOT_FOUND, status)
        }
    }

    @Test
    fun `searchSessions returns 200 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val sessions = List(10) { sessionFactory.createRandomGamingSession(game.id, player.id) }
        val request = Request(Method.GET, "$URI_PREFIX/sessions")
            .query("gameId", game.id.toString())
            .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                val response = Json.decodeFromString<SessionListResponse>(bodyString()).sessions
                assertTrue {
                    sessions.all { x ->
                        response.contains(SessionResponse(x))
                    }
                }
            }
    }

    @Test
    fun `searchSessions with params returns 200 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        sessionFactory.createRandomGamingSession(gameId = game.id)
        val request = Request(Method.GET, "$URI_PREFIX/sessions?gameId=${game.id}")
            .token(player.token)
        client(request)
            .apply {
                val response = Json.decodeFromString<SessionListResponse>(bodyString())
                assertEquals(Status.OK, status)
                assertEquals(1, response.total)
            }
    }

    @Test
    fun `searchSessions with params returns 200 for no search results`() {
        val player = playerFactory.createRandomPlayer()
        val request = Request(Method.GET, "$URI_PREFIX/sessions")
            .query("state", false.toString())
            .token(player.token)
        client(request)
            .apply {
                val response = Json.decodeFromString<SessionListResponse>(bodyString())
                assertEquals(Status.OK, status)
                assertEquals(0, response.total)
            }
    }

    @Test
    fun `updateSession returns 200 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        var session = sessionFactory.createRandomGamingSession(game.id, player.id)
        while (session.maxCapacity <= 2) {
            session = sessionFactory.createRandomGamingSession(game.id, player.id)
        }
        val requestBody = SessionUpdate(Random.nextInt(2, session.maxCapacity), plusDaysToCurrentDateTime(1).toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
            .json(requestBody)
            .token(player.token)
        client(request).apply {
            assertEquals(Status.OK, status)
            val response = Json.decodeFromString<SessionUpdate>(bodyString())
            val expectedSession =
                SessionUpdate(
                    session.copy(maxCapacity = requestBody.capacity, startingDate = requestBody.startingDate.toLocalDateTime()),
                )
            assertEquals(expectedSession, response)
        }
    }

    @Test
    fun `updateSession returns 400 for bad request`() {
        val player = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession(hostId = player.id, players = emptySet())
        val requestBody = SessionUpdate(session.maxCapacity, minusDaysToCurrentDateTime(1L).toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
            .json(requestBody)
            .token(player.token)
        client(request).apply {
            val response = Json.decodeFromString<SessionUpdate>(bodyString())
            val expectedSession =
                SessionUpdate(session.copy(maxCapacity = requestBody.capacity, startingDate = requestBody.startingDateFormatted))
            assertEquals(Status.OK, status)
            assertEquals(expectedSession, response)
        }
    }

    @Test
    fun `updateSession returns 400 for closed session`() {
        val player = playerFactory.createRandomPlayer()
        val playersInSession = List(10) { playerFactory.createRandomPlayer() }.toSet()
        val session = sessionFactory.createRandomGamingSession(
            isOpen = false,
            hostId = player.id,
            players = playersInSession,
        )
        val requestBody = SessionUpdate(session.maxCapacity + 1, plusDaysToCurrentDateTime(1L).toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
            .json(requestBody)
            .token(player.token)
        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `updateSession returns 400 for past date`() {
        val ms = 10L
        val player = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession(
            date = plusMillisecondsToCurrentDateTime(ms),
            hostId = player.id,
        )
        val requestBody = SessionUpdate(session.maxCapacity + 1, session.startingDate.toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
            .json(requestBody)
            .token(player.token)
        Thread.sleep(2 * ms)
        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `updateSession returns 400 for lower than allowed capacity`() {
        val player = playerFactory.createRandomPlayer()
        val playersIsSession = List(10) { playerFactory.createRandomPlayer() }.toSet()
        val session = sessionFactory.createRandomGamingSession(hostId = player.id, players = playersIsSession)
        val requestBody = SessionUpdate(SESSION_MIN_CAPACITY, session.startingDate.toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
            .json(requestBody)
            .token(player.token)
        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `updateSession returns 403 for non-host`() {
        val player = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession()
        val requestBody = SessionUpdate(session.maxCapacity, plusDaysToCurrentDateTime(365L * 2).toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
            .json(requestBody)
            .token(player.token)
        client(request).apply {
            assertEquals(Status.FORBIDDEN, status)
        }
    }

    @Test
    fun `updateSession returns 404 for non-existing session`() {
        val player = playerFactory.createRandomPlayer()
        val requestBody = SessionUpdate(SESSION_MIN_CAPACITY, plusDaysToCurrentDateTime(1L).toLong())
        val request = Request(Method.PUT, "$URI_PREFIX/sessions/999999")
            .json(requestBody)
            .token(player.token)
        client(request).apply {
            assertEquals(Status.NOT_FOUND, status)
        }
    }

    @Test
    fun `deleteSession returns 204 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val game = gameFactory.createRandomGame()
        val session = sessionFactory.createRandomGamingSession(game.id, player.id)
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}")
            .json("")
            .token(player.token)
        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun `deleteSession returns 403 for non-host`() {
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession(hostId = player2.id)
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}")
            .json("")
            .token(player.token)
        client(request).apply {
            assertEquals(Status.FORBIDDEN, status)
        }
    }

    @Test
    fun `deleteSession returns 404 for non-existing session`() {
        val player = playerFactory.createRandomPlayer()
        val request = Request(Method.DELETE, "$URI_PREFIX/sessions/999999")
            .json("")
            .token(player.token)
        client(request).apply {
            assertEquals(Status.NOT_FOUND, status)
        }
    }

    @Test
    fun `getSession returns 200 for good request`() {
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val newSession = sessionFactory.createRandomGamingSession(game.id, player.id)
        val request = Request(Method.POST, "$URI_PREFIX/sessions/${newSession.id}")
            .json("")
            .token(player2.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
            }
    }
}
