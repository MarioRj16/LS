
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionResponse
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionsTests : IntegrationTests() {
    companion object {
        val player = PlayerFactory(db.players).createRandomPlayer()
        val game = GameFactory(db.games).createRandomGame()
        val sessions: List<GamingSession> =
            searchHelpSessions(10, GamingSessionFactory(db.gamingSessions)::createRandomGamingSession, game.id, player.id)
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
                assertDoesNotThrow { Json.decodeFromString<SessionResponse>(bodyString()) }
            }
    }

    @Test
    fun addPlayerToSession() {
        val session = GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id, player.id)
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
            GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id, player.id, setOf(player))
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
        val requestBody = SessionSearch(game.id)
        val request =
            Request(Method.GET, "$URI_PREFIX/sessions")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                val response: List<GamingSession> = Json.decodeFromString<List<GamingSession>>(bodyString())
                assertTrue {
                    sessions.all { x ->
                        response.contains(x)
                    }
                }
            }
    }

    @Test
    fun updateSession() {
        val session = GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id, user!!.playerId)
        val requestBody =
            SessionUpdate(Random.nextInt(2, session.maxCapacity), plusDaysToCurrentDateTime(1))
        val request =
            Request(Method.PUT, "$URI_PREFIX/sessions/${session.id}").json(requestBody).token(user!!.token)
        client(request).apply {
            assertEquals(Status.OK, status)
            val response = Json.decodeFromString<GamingSession>(bodyString())
            val expectedSession =
                session.copy(maxCapacity = requestBody.capacity, startingDate = requestBody.startingDate)
            assertEquals(expectedSession, response)
        }
    }

    @Test
    fun deleteSession() {
        val session = GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id, user!!.playerId)
        val request =
            Request(Method.DELETE, "$URI_PREFIX/sessions/${session.id}").json("").token(user!!.token)
        client(request).apply {
            assertEquals(Status.NO_CONTENT, status)
        }
    }

    @Test
    fun getSession() {
        val newSession = GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id, player.id)
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
