
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionResponse
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionsTests : IntegrationTests(){


    companion object {
        val game=GameFactory(db.games).createRandomGame()
        val sessions:List<GamingSession> = searchHelpSessions(10,GamingSessionFactory(db.gamingSessions)::createRandomGamingSession,game.id)
    }
    @Test
    fun createSession(){
        val requestBody = SessionCreate(game.id,4, plusDaysToCurrentDateTime(1L))
        val request = Request(Method.POST, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED,status)
                assertDoesNotThrow { Json.decodeFromString<SessionResponse>(bodyString()) }
            }
    }

    @Test
    fun addPlayerToSession(){
        val session=GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id)
        val request = Request(Method.POST, "$URI_PREFIX/sessions/${session.id}")
            .json("")
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }
    }

    @Test
    fun searchSessions(){
        val requestBody= SessionSearch(game.id)
        val request = Request(Method.GET, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
                val response:List<GamingSession> = Json.decodeFromString<List<GamingSession>>(bodyString())
                assertTrue {
                    sessions.all{x->
                        response.contains(x)
                    }
                }
            }
    }



    @Test
    fun getSession(){
        val newSession=GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id)
        val request = Request(Method.POST, "$URI_PREFIX/sessions/${newSession.id}")
            .json("")
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }

    }




}