package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.factories.PlayerFactory
import kotlin.test.assertEquals

class PlayersServicesTests : PlayerServices(DataMem()) {

    private lateinit var bearerToken: String
    private lateinit var user: Player
    private val playerFactory = PlayerFactory(db.players)

    @BeforeEach
    fun setUp() {
        db.reset()
        user = playerFactory.createRandomPlayer()
        bearerToken = "Bearer ${user.token}"
    }

    @Test
    fun `createPlayer() returns player successfully`() {
        val name = "testName"
        val email = "testEmail@gmail.com"
        val playerInfo = """
            {
                "name": "$name",
                "email": "$email"
            }
        """.trimIndent()
        val player = createPlayer(playerInfo)
        assertEquals(name, player.name)
        assertEquals(email, player.email)
    }

    @Test
    fun `getPlayer() returns player successfully`() {
        val returnedPlayer = getPlayer(user.id, bearerToken)
        assertEquals(user, returnedPlayer)
    }

    @Test
    fun `getPlayer() throws exception for null id`() {
        assertThrows<IllegalArgumentException> {
            getPlayer(null, bearerToken)
        }
    }
}