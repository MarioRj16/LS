package pt.isel.ls.data

import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.generateRandomGameSearch
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesTests : AbstractDataTests() {
    private val genres =
        listOf(
            "Role Playing Game",
            "Action",
            "First Person Shooter",
            "Simulation",
            "Sports",
        ).mapIndexed { idx, name -> Genre(idx, name) }

    @Test
    fun `create() return game successfully`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genres = setOf(genres[0])
        val game = games.create(name, developer, genres)

        assertTrue(game.id == 3)
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
    fun `search() returns games successfully`() {
        var searchResults = games.search(generateRandomGameSearch(), DEFAULT_LIMIT, DEFAULT_SKIP)

        assertTrue(searchResults.isEmpty())

        val game = gameFactory.createRandomGame()
        val game2 = gameFactory.createRandomGame()
        val game3 = gameFactory.createRandomGame()

        searchResults = games.search(generateRandomGameSearch(true), 2, DEFAULT_SKIP)

        assertTrue(searchResults.size == 2)
        assertContains(searchResults, game)
        assertContains(searchResults, game2)

        searchResults = games.search(generateRandomGameSearch(true), DEFAULT_LIMIT, 2)

        assertTrue(searchResults.size == 1)
        assertContains(searchResults, game3)
    }
}
