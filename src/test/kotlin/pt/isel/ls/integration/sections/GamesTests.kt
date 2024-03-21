
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.domain.Genre
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.GameFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class GamesTests:IntegrationTests(){


    @Test
    fun createGame(){
        val requestBody= GameCreate("Test","developer1",setOf(Genre("Horror"),Genre("MOBA")))
        val request = Request(Method.POST, "$URI_PREFIX/games")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED,status)
            }
    }

    @Test
    fun emptySearchGames(){
        val list=searchHelpGame(10,GameFactory(db.games)::createRandomGame )
        val requestBody=GameSearch()
        val request = Request(Method.GET, "$URI_PREFIX/games")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }
    }

    @Test
    fun searchGames(){
        val list= searchHelpGame(10,GameFactory(db.games)::createRandomGame )
        val requestBody=GameSearch("Developer1",setOf(Genre("RPG")))
        val request = Request(Method.GET, "$URI_PREFIX/games")
            .json(requestBody)
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }
    }


    @Test
    fun getGame(){
        val game=GameFactory(db.games).createRandomGame()
        val request = Request(Method.GET, "$URI_PREFIX/games/${game.id}")
            .json("")
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }
    }
}