package pt.isel.ls.data

import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesTests : AbstractDataTests() {

    @Test
    fun `create() return game successfully`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genres = genreFactory.random()
        val game = games.create(name, developer, genres)

        assertTrue(game.id == 1)
        assertEquals(name, game.name)
        assertEquals(developer, game.developer)
        assertEquals(genres, game.genres)
    }

    @Test
    fun `get() returns game successfully`() {
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.name))
    }

    @Test
    fun `get() returns null for non existing game`() {
        val game = games.get(generateRandomString())

        assertTrue(game == null)
    }

    @Test
    fun `getById() returns game successfully`() {
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.id))
    }

    @Test
    fun `getById() returns null for non existing game`() {
        val game = games.get(1)

        assertTrue(game == null)
    }

    @Test
    fun `search() with no games returns empty list`() {
        val searchParams = GameSearch(null, null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertTrue(searchResults.isEmpty())
    }

    @Test
    fun `search() returns all games successfully`() {
        val gamesList = List(5) { gameFactory.createRandomGame() }
        val searchParams = GameSearch(null, null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(gamesList.size, searchResults.size)
        assertEquals(searchResults, gamesList)
    }

    @Test
    fun `search() by name returns games successfully`() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(game.name, null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    fun `search() by partial name returns games successfully`() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(game.name.take(1), null, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    fun `search() by developer returns games successfully`() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(null, game.developer, emptySet())
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }

    @Test
    fun `search() by genre returns games successfully`() {
        val game = gameFactory.createRandomGame()
        val searchParams = GameSearch(null, null, setOf(game.genres.random().genreId))
        val searchResults = games.search(searchParams, DEFAULT_LIMIT, DEFAULT_SKIP)

        assertEquals(1, searchResults.size)
        assertContains(searchResults, game)
    }
}
