package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.factories.PlayerFactory
import kotlin.test.assertEquals

class ServicesTests : ServicesSchema(DataMem()) {

    @BeforeEach
    fun setUp() {
        data.reset()
    }

    @Test
    fun `Token gets validated successfully`() {
        val playerFactory = PlayerFactory(data.players)
        val player = playerFactory.createRandomPlayer()
        val auth = "Bearer ${player.token}"
        assertEquals(player, bearerToken(auth))
    }

    @Test
    fun `throws exception for non existing token`() {
        assertThrows<IllegalArgumentException> {
            bearerToken("Bearer ")
        }
    }

    @Test
    fun `throws exception if token is in invalid format`() {
        assertThrows<IllegalArgumentException> {
            bearerToken("Bearer token")
        }
    }
}
