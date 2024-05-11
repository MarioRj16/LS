package pt.isel.ls.data.games

import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.DataPostgresTests
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesPostgresTests : DataPostgresTests(), GamesTests {

    @Test
    override fun createReturnGameSuccessfully() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genres = genreFactory.random()
        val game = games.create(name, developer, genres)

        assertEquals(name, game.name)
        assertEquals(developer, game.developer)
        assertEquals(genres, game.genres)
    }

    @Test
    override fun getReturnsGameSuccessfully() {
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.name))
    }

    @Test
    override fun getReturnsNullForNonExistingGame() {
        val game = games.get(generateRandomString())

        assertTrue(game == null)
    }

    @Test
    override fun getByIdReturnsGameSuccessfully() {
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.id))
    }

    @Test
    override fun getByIdReturnsNullForNonExistingGame() {
        val game = games.get(1)

        assertTrue(game == null)
    }

    @Test
    override fun searchWithNoGamesReturnsEmptyList() {
        val searchParams = GameSearch(null, null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertTrue(searchResults.isEmpty())
    }

    @Test
    override fun searchReturnsAllGamesSuccessfully() {
        val gamesList = List(5) { gameFactory.createRandomGame() }.toSet()
        val searchParams = GameSearch(null, null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP).toSet()

        assertEquals(gamesList.size, searchResults.size)
        assertEquals(searchResults, gamesList)
    }

    @Test
    override fun searchByNameReturnsGamesSuccessfully() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(game.name, null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    override fun searchByCaseInsensitiveNameReturnsGamesSuccessfully() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(game.name.uppercase(), null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    override fun searchByPartialNameReturnsGamesSuccessfully() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(game.name.take(1), null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    override fun searchByDeveloperReturnsGamesSuccessfully() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(null, game.developer, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    override fun searchByGenreReturnsGamesSuccessfully() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(null, null, setOf(game.genres.random().genreId))
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults.map { it.id }, game.id)
    }
}
