package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.factories.PlayerFactory
import java.util.UUID
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
    fun `createPlayer() returns player successfully`() {
        val name = "testName"
        val email = "testEmail@gmail.com"
        val playerInfo = PlayerCreate(name, email)
        val player = createPlayer(playerInfo)
        assertEquals(name, player.name)
        assertEquals(email, player.email)
    }

    @Test
    fun `getPlayer() returns player successfully`() {
        val returnedPlayer = getPlayer(user.id, token)
        assertEquals(user, returnedPlayer)
    }
}
