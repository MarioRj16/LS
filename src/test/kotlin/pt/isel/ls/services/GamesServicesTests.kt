package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.generateRandomGameSearch
import pt.isel.ls.utils.generateRandomString
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamesServicesTests : GamesServices(DataMem()) {
    private lateinit var token: UUID
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(data.players)
    private val gameFactory = GameFactory(data.games)

    @BeforeEach
    fun setUp() {
        data.reset()
        user = playerFactory.createRandomPlayer()
        token = user.token
    }

    @Test
    fun `createGame() returns created game id successfully`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genre = Genre(1, "Action")
        val gameCreate = GameCreate(name, developer, setOf(genre))
        val gameId = createGame(gameCreate, token)
        assertTrue(gameId == 1)
    }

    @Test
    fun `getGame() returns game successfully`() {
        val game = gameFactory.createRandomGame()
        val returnedGame = getGame(game.id, token)
        assertEquals(game, returnedGame)
    }

    @Test
    fun `searchGames() returns games successfully`() {
        val games = List(3) { gameFactory.createRandomGame() }
        val searchResults = searchGames(generateRandomGameSearch(true), token, DEFAULT_SKIP, DEFAULT_LIMIT)
        assertEquals(searchResults.size, games.size)
        assertTrue(games.all { game -> searchResults.contains(game) })
    }
}
