package pt.isel.ls.integration.sections
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random
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
    fun createSession() {
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
    fun addPlayerToSession() {
        val session =
            GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)
            .createRandomGamingSession(game.id, player.id)
        val request =
            Request(Method.POST, "$URI_PREFIX/sessions/${session.id}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
            }
    }

    @Test
    fun removePlayerFromSession() {
        val session =
            GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players).createRandomGamingSession(game.id, player.id, setOf(player))
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}/players/${player.id}")
                .json("")
                .token(player.token)

        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun searchSessions() {
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
    fun updateSession() {
        var session = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players).createRandomGamingSession(game.id, user!!.playerId)
        while (session.maxCapacity <= 2) {
            session = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players).createRandomGamingSession(game.id, user!!.playerId)
        }
        val requestBody =
            SessionUpdate(Random.nextInt(2, session.maxCapacity), plusDaysToCurrentDateTime(1))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}").json(requestBody).token(user!!.token)
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
    fun deleteSession() {
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
    fun getSession() {
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
}
