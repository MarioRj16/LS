package pt.isel.ls.services

import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.api.models.players.PlayerListElement
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.BadRequestException
import pt.isel.ls.utils.factories.PlayerFactory
import kotlin.test.assertEquals

class PlayersServicesTests : PlayersServices(DataMem()) {
    private lateinit var user: Player
    private lateinit var token: UUID
    private val playerFactory = PlayerFactory(db.players)

    @BeforeEach
    fun setUp() {
        db.reset()
        user = playerFactory.createRandomPlayer()
        token = user.token
    }

    @Test
    fun `createPlayer() should create a player successfully`() {
        val playerCreate = PlayerCreate.create()
        val createdPlayer = createPlayer(playerCreate)
        val player = getPlayer(createdPlayer.playerId, createdPlayer.token)
        assertEquals(playerCreate.name, player.name)
        assertEquals(playerCreate.email, player.email)
    }

    @Test
    fun `createPlayer() throws BadRequestException when email is not unique`() {
        val exception = assertThrows<BadRequestException> {
            createPlayer(PlayerCreate.create(email = user.email))
        }
        assertEquals("The given email is not unique", exception.message)
    }

    @Test
    fun `createPlayer() throws BadRequestException when username is not unique`() {
        val exception = assertThrows<BadRequestException> {
            createPlayer(PlayerCreate.create(name = user.name))
        }
        assertEquals("The given username is not unique", exception.message)
    }

    @Test
    fun `getPlayer() returns player successfully`() {
        val returnedPlayer = getPlayer(user.id, token)
        assertEquals(PlayerDetails(user), returnedPlayer)
    }

    @Test
    fun `searchPlayers() returns players successfully`() {
        val players = searchPlayers(PlayerSearch(user.name.substring(0, 2)), token, DEFAULT_SKIP, DEFAULT_LIMIT)
        assertEquals(1, players.total)
        assertEquals(1, players.players.size)
        assertEquals(PlayerListElement(user), players.players[0])
    }
}
