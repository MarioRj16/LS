package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesServicesTests : GamesServices(DataMem()) {
    private lateinit var bearerToken: String
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(db.players)
    private val gameFactory = GameFactory(db.games)

    @BeforeEach
    fun setUp() {
        db.reset()
        user = playerFactory.createRandomPlayer()
        bearerToken = "Bearer ${user.token}"
    }

    @Test
    fun `createGame() returns created game id successfully`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genre = Genre(1, "Action")
        val gameCreate = GameCreate(name, developer, setOf(genre))
        val gameId = createGame(gameCreate, bearerToken)
        assertTrue(gameId == 1)
    }

    @Test
    fun `getGame() returns game successfully`() {
        val game = gameFactory.createRandomGame()
        val returnedGame = getGame(game.id, bearerToken)
        assertEquals(game, returnedGame)
    }

    @Test
    fun `getGame() throws exception for null id`() {
        assertThrows<IllegalArgumentException> {
            getGame(null, bearerToken)
        }
    }

    @Test
    fun `searchGames() returns games successfully`() {
        val game1 = gameFactory.createRandomGame()
        val game2 = gameFactory.createRandomGame()
        val game3 = gameFactory.createRandomGame()
        val searchResults = searchGames(GameSearch(), bearerToken, null, null)
        assertTrue(searchResults.size == 3)
        assertContains(searchResults, game1)
        assertContains(searchResults, game2)
        assertContains(searchResults, game3)
    }
}
