
import kotlinx.datetime.toLocalDateTime
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.GameFactory
import pt.isel.ls.utils.GamingSessionFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class GamingSessionsTests : IntegrationTests(){

    @Test
    fun createSession(){
        val game=GameFactory(db.games).createRandomGame()
        val requestBody = SessionCreate(game.id,4, "2025-03-20T12:00:00".toLocalDateTime())
        val request = Request(Method.POST, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED,status)
            }
    }

    @Test
    fun addPlayerToSession(){
        val game=GameFactory(db.games).createRandomGame()
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
        val game=GameFactory(db.games).createRandomGame()
        val sessions= searchHelpSessions(10,GamingSessionFactory(db.gamingSessions)::createRandomGamingSession,game.id)
        val requestBody= SessionSearch(game.id)
        val request = Request(Method.GET, "$URI_PREFIX/sessions")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }
    }


    @Test
    fun getSession(){
        val game=GameFactory(db.games).createRandomGame()
        val session=GamingSessionFactory(db.gamingSessions).createRandomGamingSession(game.id)
        val request = Request(Method.POST, "$URI_PREFIX/sessions/${session.id}")
            .json("")
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }

    }


}