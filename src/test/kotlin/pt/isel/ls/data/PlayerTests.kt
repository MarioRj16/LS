package pt.isel.ls.data

import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.utils.Email
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlayerTests : AbstractDataTests() {
    @Test
    fun `create() creates player successfully`() {
        val name = "testName"
        val email = Email("test@email.com")
        val playerCreate = PlayerCreate(name, email)
        val player = players.create(playerCreate)

        assertEquals(name, player.name)
        assertEquals(email, player.email)
        assertNotNull(player.token)
        assertTrue(player.id > 0)
    }

    @Test
    fun `get() returns player successfully`() {
        val player = playerFactory.createRandomPlayer()

        assertEquals(player, players.get(player.id))
    }

    @Test
    fun `get() returns null for non existing player`() {
        val player = players.get(1)

        assertTrue(player == null)
    }
}
