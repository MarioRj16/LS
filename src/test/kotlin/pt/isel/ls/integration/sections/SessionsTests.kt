package pt.isel.ls.integration.sections
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.SESSION_MAX_CAPACITY
import pt.isel.ls.SESSION_MIN_CAPACITY
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionCreateResponse
import pt.isel.ls.api.models.sessions.SessionListResponse
import pt.isel.ls.api.models.sessions.SessionResponse
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.domain.Session
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.minusDaysToCurrentDateTime
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import pt.isel.ls.utils.plusMillisecondsToCurrentDateTime
import kotlin.random.Random
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SessionsTests : IntegrationTests() {
    companion object {
        private const val REPS = 10
        val player = PlayerFactory(db.players).createRandomPlayer()
        val game = GameFactory(db.games, db.genres).createRandomGame()
        val sessions: List<Session> =
            searchHelpSessions(
                REPS,
                GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)::createRandomGamingSession,
                game.id,
                player.id)
    }

    @Test
    fun `createSession returns 201 for good response`() {
        val game = gameFactory.createRandomGame()
        val requestBody = SessionCreate(game.id, 4, plusDaysToCurrentDateTime(1L))
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<SessionCreateResponse>(bodyString()) }
            }
    }

    @Test
    fun `createSession returns 400 for non existing game`() {
        val requestBody = SessionCreate(999999, 4, plusDaysToCurrentDateTime())
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createSession returns 400 for invalid capacity`() {
        val game = gameFactory.createRandomGame()
        val requestBody = SessionCreate(game.id, SESSION_MAX_CAPACITY+1, plusDaysToCurrentDateTime())
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createSession returns 400 for past starting date`() {
        val game = gameFactory.createRandomGame()
        val requestBody = SessionCreate(game.id, 4, minusDaysToCurrentDateTime(1L))
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions")
                .json(requestBody)
                .token(user!!.token)
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
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions/${session.id}")
                .json("")
                .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
            }
    }

    @Test
    fun `addPlayerToSession returns 401 for non-existing authorized player`() {
        val game = gameFactory.createRandomGame()
        val session = sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id)
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions/${session.id}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.UNAUTHORIZED, status)
            }
    }

    @Test
    fun `addPlayerToSession returns 404 for non-existing session`() {
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions/99999")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.NOT_FOUND, status)
            }
    }

    @Test
    fun `removePlayerFromSession (by host) returns 204 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player2))
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player2.id}")
                .json("")
                .token(player.token)

        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun `removePlayerFromSession (by player himself) returns 204 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player2))
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player2.id}")
                .json("")
                .token(player2.token)

        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 401 for unauthorized player`() {
        val player = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player))
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player.id}")
                .json("")
                .token(user!!.token)

        client(request).apply {
            assertEquals(Status.UNAUTHORIZED, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 400 for non-existing session`() {
        val player = playerFactory.createRandomPlayer()
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/99999/players/${player.id}")
                .json("")
                .token(player.token)

        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 400 for player not is session`() {
        val player = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = emptySet())
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player.id}")
                .json("")
                .token(player.token)

        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `removePlayerFromSession returns 404 for non-existing player`() {
        val player = playerFactory.createRandomPlayer()
        val session =
            sessionFactory.createRandomGamingSession(gameId = game.id, hostId = player.id, players = setOf(player))
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/9999")
                .json("")
                .token(player.token)

        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `searchSessions returns 200 for good request`() {
        val request =
            Request(Method.GET, "$URI_PREFIX/sessions")
                .query("gameId", game.id.toString())
                .token(user!!.token)
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
        val session  = sessionFactory.createRandomGamingSession()
        val request =
            Request(Method.GET, "$URI_PREFIX/sessions")
                .query("gameId", game.id.toString())
                .token(user!!.token)
        client(request)
            .apply {
                val response = Json.decodeFromString<SessionListResponse>(bodyString()).sessions
                assertEquals(Status.OK, status)
                assertContains(response, SessionResponse(session))
            }
    }

    @Test
    fun `searchSessions with params returns 200 for no search results`() {
        val request =
            Request(Method.GET, "$URI_PREFIX/sessions")
                .query("state", false.toString())
                .token(user!!.token)
        client(request)
            .apply {
                val response = Json.decodeFromString<SessionListResponse>(bodyString()).sessions
                assertEquals(Status.OK, status)
                assertEquals(0, response.size)
            }
    }

    @Test
    fun `updateSession returns 200 for good request`() {
        var session = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players).createRandomGamingSession(game.id, user!!.playerId)
        while (session.maxCapacity <= 2) {
            session = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players).createRandomGamingSession(game.id, user!!.playerId)
        }
        val requestBody =
            SessionUpdate(Random.nextInt(2, session.maxCapacity), plusDaysToCurrentDateTime(1))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
                .json(requestBody)
                .token(user!!.token)
        client(request).apply {
            assertEquals(Status.OK, status)
            val response = Json.decodeFromString<SessionUpdate>(bodyString())
            val expectedSession =
                SessionUpdate(
                    session.copy(maxCapacity = requestBody.capacity, startingDate = requestBody.startingDate),
                )
            assertEquals(expectedSession, response)
        }
    }

    @Test
    fun `updateSession returns 400 for bad request`() {
        val session = sessionFactory.createRandomGamingSession(hostId = user!!.playerId, players = emptySet())
        val requestBody = SessionUpdate(session.maxCapacity, minusDaysToCurrentDateTime(1L))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
                .json(requestBody)
                .token(user!!.token)
        client(request).apply {
            val response = Json.decodeFromString<SessionUpdate>(bodyString())
            val expectedSession =
                SessionUpdate(session.copy(maxCapacity = requestBody.capacity, startingDate = requestBody.startingDate))
            assertEquals(Status.OK, status)
            assertEquals(expectedSession, response)
        }
    }

    @Test
    fun `updateSession returns 400 for closed session`() {
        val playersInSession = List(10){ playerFactory.createRandomPlayer() }.toSet()
        val session = sessionFactory.createRandomGamingSession(
                isOpen = false,
                hostId = user!!.playerId,
                players = playersInSession
            )
        val requestBody = SessionUpdate(session.maxCapacity+1, plusDaysToCurrentDateTime(1L))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
                .json(requestBody)
                .token(user!!.token)
        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `updateSession returns 400 for past date`() {
        val ms = 1L
        val session = sessionFactory.createRandomGamingSession(
            date = plusMillisecondsToCurrentDateTime(ms),
            hostId = user!!.playerId,
        )
        Thread.sleep(2*ms)
        val requestBody = SessionUpdate(session.maxCapacity+1, session.startingDate)
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
                .json(requestBody)
                .token(user!!.token)
        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `updateSession returns 400 for lower than allowed capacity`() {
        val playersIsSession = List(10){ playerFactory.createRandomPlayer() }.toSet()
        val session = sessionFactory.createRandomGamingSession(hostId = user!!.playerId, players = playersIsSession)
        val requestBody = SessionUpdate(SESSION_MIN_CAPACITY, session.startingDate)
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
                .json(requestBody)
                .token(user!!.token)
        client(request).apply {
            assertEquals(Status.BAD_REQUEST, status)
        }
    }

    @Test
    fun `updateSession returns 403 for non-host`() {
        val player = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession()
        val requestBody = SessionUpdate(session.maxCapacity, plusDaysToCurrentDateTime(365L*2))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}")
                .json(requestBody)
                .token(player.token)
        client(request).apply {
            assertEquals(Status.FORBIDDEN, status)
        }
    }

    @Test
    fun `updateSession returns 404 for non-existing session`() {
        val requestBody = SessionUpdate(SESSION_MIN_CAPACITY, plusDaysToCurrentDateTime(1L))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/999999")
                .json(requestBody)
                .token(user!!.token)
        client(request).apply {
            assertEquals(Status.NOT_FOUND, status)
        }
    }

    @Test
    fun `deleteSession returns 204 for good request`() {
        val session =
            GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)
                .createRandomGamingSession(game.id, user!!.playerId)
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}").json("").token(user!!.token)
        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun `deleteSession returns 403 for non-host`() {
        val player = playerFactory.createRandomPlayer()
        val session = sessionFactory.createRandomGamingSession(hostId = user!!.playerId)
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}")
                .json("")
                .token(player.token)
        client(request).apply {
            assertEquals(Status.FORBIDDEN, status)
        }
    }

    @Test
    fun `deleteSession returns 404 for non-existing session`() {
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/999999")
                .json("")
                .token(user!!.token)
        client(request).apply {
            assertEquals(Status.NOT_FOUND, status)
        }
    }

    @Test
    fun `getSession returns 200 for good request`() {
        val newSession = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players).createRandomGamingSession(game.id, player.id)
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions/${newSession.id}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
            }
    }

    @Test
    fun `getSession returns 400 for non-existing session`() {
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions/999999")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.NOT_FOUND, status)
            }
    }
}
