package pt.isel.ls.services

import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.exceptions.BadRequestException
import pt.isel.ls.utils.factories.PlayerFactory
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertEquals

class PlayersServicesTests : PlayerServices(DataMem()) {
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
        val name = "testName"
        val email = Email("testEmail@gmail.com")
        val playerInfo = PlayerCreate(name, email)
        val createdPlayer = createPlayer(playerInfo)
        val player = getPlayer(createdPlayer.playerId, createdPlayer.token)
        assertEquals(name, player.name)
        assertEquals(email, player.email)
    }

    @Test
    fun `createPlayer() throws ConflictException when email is not unique`() {
        val exception = assertThrows<BadRequestException> {
            createPlayer(PlayerCreate(generateRandomString(), user.email))
        }
        assertEquals("The given email is not unique", exception.message)
    }

    @Test
    fun `createPlayer() throws ConflictException when username is not unique`() {
        val exception = assertThrows<BadRequestException> {
            createPlayer(PlayerCreate(user.name, generateRandomEmail()))
        }
        assertEquals("The given username is not unique", exception.message)
    }

    @Test
    fun `getPlayer() returns player successfully`() {
        val returnedPlayer = getPlayer(user.id, token)
        assertEquals(PlayerDetails(user), returnedPlayer)
    }
}
