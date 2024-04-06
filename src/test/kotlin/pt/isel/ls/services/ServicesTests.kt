package pt.isel.ls.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.factories.PlayerFactory
import java.util.UUID
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
        assertEquals(player, bearerToken(player.token))
    }

    @Test
    fun `throws exception for non existing token`() {
        assertThrows<AuthorizationException> {
            bearerToken(UUID.randomUUID())
        }
    }
}
