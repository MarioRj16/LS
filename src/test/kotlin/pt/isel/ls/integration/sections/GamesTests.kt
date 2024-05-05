package pt.isel.ls.integration.sections
import java.util.UUID
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameCreateResponse
import pt.isel.ls.api.models.games.GameDetails
import pt.isel.ls.api.models.games.GameListResponse
import pt.isel.ls.api.models.games.GameResponse
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.domain.Genre
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.generateRandomGameSearch
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesTests : IntegrationTests() {
    companion object {
        val list = searchHelpGame(20, gameFactory::createRandomGame)
    }

    @Test
    fun `getGame returns 200 for good request`() {
        val game = gameFactory.createRandomGame()
        val request =
            Request(Method.GET, "$URI_PREFIX/games/${game.id}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                assertEquals(GameDetails(game), Json.decodeFromString<GameDetails>(bodyString()))
            }
    }

    @Test
    fun `getGame returns 404 for non-existing game`() {
        val request =
            Request(Method.GET, "$URI_PREFIX/games/999")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.NOT_FOUND, status)
            }
    }

    @Test
    fun `createGames returns 201 for good request`() {
        val requestBody = GameCreate("Test", "developer1", setOf(1))
        val request =
            Request(Method.POST, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<GameCreateResponse>(bodyString()) }
            }
    }

    @Test
    fun `createGames returns 400 for non-unique name`() {
        val game = gameFactory.createRandomGame()
        val requestBody = GameCreate(game.name, generateRandomString(), genresFactory.random().map { it.genreId }.toSet())
        val request =
            Request(Method.POST, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createGames returns 400 for blank name`() {
        val genres = genresFactory.random().map { it.genreId }.toSet()
        val requestBody = GameCreate("", generateRandomString(), genres)
        val request =
            Request(Method.POST, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createGames returns 400 for blank developer`() {
        val genres = genresFactory.random().map { it.genreId }.toSet()
        val requestBody = GameCreate(generateRandomString(), "", genres)
        val request =
            Request(Method.POST, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createGames returns 400 for empty set of genres`() {
        val requestBody = GameCreate(generateRandomString(), generateRandomString(), emptySet())
        val request =
            Request(Method.POST, "$URI_PREFIX/games")
                .json(requestBody)
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `gameSearch with params returns 200 for good request`() {
        val search = generateRandomGameSearch()
        val request =
            Request(Method.GET, "$URI_PREFIX/games?developer=${search.developer}&genres=${search.genres.joinToString(",")}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                val response = Json.decodeFromString<GameListResponse>(bodyString())
                assertTrue {
                    list.filter { it -> it.developer == search.developer && it.genres.containsAll(search.genres.map { Genre(it, "") }) }
                        .map { GameResponse(it) }
                        .all { x ->
                            response.games.contains(x)
                        }
                }
            }
    }

    @Test
    fun `gameSearch with params returns 200 for no search results`() {
        val search = GameSearch(UUID.randomUUID().toString(), UUID.randomUUID().toString(), setOf(1))
        val request =
            Request(Method.GET, "$URI_PREFIX/games?developer=${search.developer}&genres=${search.genres.joinToString(",")}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                val response = Json.decodeFromString<GameListResponse>(bodyString())
                assertEquals(Status.OK, status)
                assertEquals(0, response.games.size)
            }
    }

    @Test
    fun `gameSearch returns 200 for good request`() {
        val request =
            Request(Method.GET, "$URI_PREFIX/games")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                val response = Json.decodeFromString<GameListResponse>(bodyString())
                assertTrue {
                    list.filter { it.developer == "Developer1" && it.genres.contains(Genre(1, "RPG")) }
                        .map { GameResponse(it) }
                        .all { x ->
                            response.games.contains(x)
                        }
                }
            }
    }
}
