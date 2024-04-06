package pt.isel.ls.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.games.GameCreate
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
        val gameCreate = GameCreate(name, developer, genres)
        val game = games.create(gameCreate)

        assertTrue(game.id == 1)
        assertEquals(name, game.name)
        assertEquals(developer, game.developer)
        assertEquals(genres, game.genres)
    }

    @Test
    fun `create() throws exception for non unique name`() {
        val name = generateRandomString()
        val gameCreate1 = GameCreate(name, generateRandomString(), setOf(genres[0]))
        val gameCreate2 = GameCreate(name, generateRandomString(), setOf(genres[1]))
        games.create(gameCreate1)

        assertThrows<IllegalArgumentException> {
            games.create(gameCreate2)
        }
    }

    @Test
    fun `get() returns game successfully`() {
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.name))
    }

    @Test
    fun `get() throws exception for non existing game`() {
        assertThrows<NoSuchElementException> {
            games.get("game")
        }
    }

    @Test
    fun `getById() returns game successfully`() {
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.id))
    }

    @Test
    fun `getById() throws exception for non existing game`() {
        assertThrows<NoSuchElementException> {
            games.get(1)
        }
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
