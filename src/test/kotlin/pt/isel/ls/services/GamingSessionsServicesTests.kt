package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.GameFactory
import pt.isel.ls.utils.GamingSessionFactory
import pt.isel.ls.utils.PlayerFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionsServicesTests: SessionServices(DataMem()) {
    private lateinit var bearerToken: String
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(db.players)
    private val gamingSessionFactory = GamingSessionFactory(db.gamingSessions)
    private val gameFactory = GameFactory(db.games)

    @BeforeEach
    fun setUp(){
        db.reset()
        user = playerFactory.createRandomPlayer()
        bearerToken = "Bearer ${user.token}"
    }

    @Test
    fun `createSession() returns sessionResponse successfully`(){
        val game = gameFactory.createRandomGame()
        val capacity = 2
        val startingDate = "2025-03-20T12:00:00"
        val input = """
            {
                "gameId": ${game.id},
                "capacity": $capacity,
                "startingDate": "$startingDate"
            }
        """.trimIndent()
        val sessionResponse = createSession(input, bearerToken)
        val expectedId = 1
        assertEquals(expectedId, sessionResponse.id)
    }

    @Test
    fun `getSession() returns gaming session successfully`(){
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)
        val returnedSession = getSession(session.id, bearerToken)
        assertEquals(session, returnedSession)
    }

    @Test
    fun `getSession() throws exception for null id`(){
        assertThrows<IllegalArgumentException> {
            getSession(null, bearerToken)
        }
    }

    @Test
    fun `searchSessions() returns gaming sessions successfully`(){
        val game = gameFactory.createRandomGame()
        val session1 = gamingSessionFactory.createRandomGamingSession(game.id)
        val session2 = gamingSessionFactory.createRandomGamingSession(game.id)

        val input = """
            {
                "game": ${game.id}
            }
        """.trimIndent()
        val gamingSessions = searchSessions(input, bearerToken, null, null)

        assertTrue(gamingSessions.size == 2)
        assertContains(gamingSessions, session1)
        assertContains(gamingSessions, session2)
    }

    @Test
    fun `addPlayerToSession() returns id of added player successfully`(){
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)
        val returnedPlayerId = addPlayerToSession(session.id, bearerToken)
        assertEquals(user.id, returnedPlayerId)
    }

    @Test
    fun `addPlayerToSession() throws exception for null id`(){
        assertThrows<IllegalArgumentException> {
            addPlayerToSession(null, bearerToken)
        }
    }
}