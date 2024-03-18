package pt.isel.ls.data

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.AppFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesTests: DataMem() {

    val default_skip = 0
    val default_limit = 30

    private companion object: AppFactory(DataMem())

    private val genres = listOf(
        Genre("Role Playing Game"),
        Genre("Action"),
        Genre("First Person Shooter"),
        Genre("Simulation"),
        Genre("Sports")
    )

    @BeforeEach
    fun beforeEachTest() = reset()


    @Test
    fun `create() return game successfully`(){
        val name = "testName"
        val developer = "testDeveloper"
        val genres = setOf(genres[0])
        val game = games.create(name, developer, genres)

        assertTrue(game.id == 1)
        assertEquals(name, game.name)
        assertEquals(developer, game.developer)
        assertEquals(genres, game.genres)
    }

    @Test
    fun `create() throws exception for non unique name`(){
        games.create("name", "developer1", setOf(genres[0]))

        assertThrows<IllegalArgumentException> {
            games.create(name = "name", developer = "developer2", genres = setOf(genres[1]))
        }
    }

    @Test
    fun `create() throws exception for no genres`(){
        assertThrows<IllegalArgumentException> {
            games.create(name = "name", developer = "developer", genres = setOf())
        }
    }

    @Test
    fun `get() returns game successfully`(){
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.name))
    }

    @Test
    fun `get() throws exception for non existing game`(){
        assertThrows<NoSuchElementException> {
            games.get("game")
        }
    }

    @Test
    fun `getById() returns game successfully`(){
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.getById(game.id))
    }

    @Test
    fun `getById() throws exception for non existing game`(){
        assertThrows<NoSuchElementException> {
            games.getById(1)
        }
    }

    @Test
    fun `search() returns games successfully`(){
        var searchResults = games.search(null, null, default_limit, default_skip)

        assertTrue(searchResults.isEmpty())

        val game = gameFactory.createRandomGame()
        val game2 = gameFactory.createRandomGame()
        val game3 = gameFactory.createRandomGame()

        searchResults = games.search(null, null, 2, default_skip)

        assertTrue(searchResults.size == 2)
        assertContains(searchResults, game)
        assertContains(searchResults, game2)

        searchResults = games.search(null, null, default_limit, 2)

        assertTrue(searchResults.size == 1)
        assertContains(searchResults, game3)
    }
}