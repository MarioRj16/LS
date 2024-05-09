package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameDetails
import pt.isel.ls.api.models.games.GameResponse
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.BadRequestException
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
    private val gameFactory = GameFactory(data.games, data.genres)

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
        val gameCreate = GameCreate(name, developer, setOf(1))
        val gameId = createGame(gameCreate, token)
        assertTrue(gameId.id == 1)
    }

    @Test
    fun `createGame() throws exception for non unique name`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val gameCreate = GameCreate(name, developer, setOf(1))
        createGame(gameCreate, token)
        assertThrows<BadRequestException> {
            createGame(gameCreate, token)
        }
    }

    @Test
    fun `createGame() throws exception for non existing genre`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val gameCreate = GameCreate(name, developer, setOf(20))
        assertThrows<IllegalArgumentException> {
            createGame(gameCreate, token)
        }
    }

    @Test
    fun `getGame() returns game successfully`() {
        val game = gameFactory.createRandomGame()
        val expected = GameDetails(game)
        assertEquals(expected, getGame(game.id, token))
    }

    @Test
    fun `searchGames() returns games successfully`() {
        val games = List(3) { gameFactory.createRandomGame() }
        val searchResults = searchGames(generateRandomGameSearch(true), token, DEFAULT_SKIP, DEFAULT_LIMIT)
        assertEquals(searchResults.games.size, games.size)
        assertTrue(games.all { game -> searchResults.games.contains(GameResponse(game)) })
    }
}
