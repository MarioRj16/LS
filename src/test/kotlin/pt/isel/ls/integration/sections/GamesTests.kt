
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameResponse
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.generateRandomGameSearch
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesTests : IntegrationTests() {
    companion object {
        val list = searchHelpGame(20, GameFactory(db.games)::createRandomGame)
    }

    @Test
    fun createGame() {
        val requestBody = GameCreate("Test", "developer1", setOf(Genre(1, "Horror")))
        val request =
            Request(Method.POST, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<GameResponse>(bodyString()) }
            }
    }

    @Test
    fun noParametersSearchGames() {
        val requestBody = generateRandomGameSearch()
        val request =
            Request(Method.GET, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                val response = Json.decodeFromString<List<Game>>(bodyString())
                assertTrue {
                    list.all { x ->
                        response.contains(x)
                    }
                }
            }
    }

    @Test
    fun searchGames() {
        val request =
            Request(Method.GET, "$URI_PREFIX/games")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                val response = Json.decodeFromString<List<Game>>(bodyString())
                assertTrue {
                    list.filter { it.developer == "Developer1" && it.genres.contains(Genre(1, "RPG")) }
                        .all { x ->
                            response.contains(x)
                        }
                }
            }
    }

    @Test
    fun getGame() {
        val game = GameFactory(db.games).createRandomGame()
        val request =
            Request(Method.GET, "$URI_PREFIX/games/${game.id}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                assertEquals(game, Json.decodeFromString<Game>(bodyString()))
            }
    }
}
