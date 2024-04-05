package pt.isel.ls.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.utils.exceptions.ConflictException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlayerTests : AbstractDataTests() {
    @Test
    fun `create() creates player successfully`() {
        val name = "testName"
        val email = "test@email.com"
        val playerCreate = PlayerCreate(name, email)
        val player = players.create(playerCreate)

        assertEquals(name, player.name)
        assertEquals(email, player.email)
        assertNotNull(player.token)
        assertTrue(player.id == 1)
    }

    @Test
    fun `create() throws exception for non unique email`() {
        val email = "email@email.com"
        val playerCreate = PlayerCreate("name", email)
        players.create(playerCreate)
        assertThrows<ConflictException> {
            players.create(playerCreate.copy(name = "name2"))
        }
    }

    @Test
    fun `create() throws exception for email in invalid format`() {
        assertThrows<IllegalArgumentException> {
            val playerCreate = PlayerCreate("name", "email")
            players.create(playerCreate)
        }

        assertThrows<IllegalArgumentException> {
            val playerCreate = PlayerCreate("name", "email@")
            players.create(playerCreate)
        }

        assertThrows<IllegalArgumentException> {
            val playerCreate = PlayerCreate("name", "email@email.@.uk")
            players.create(playerCreate)
        }
    }

    @Test
    fun `get() returns player successfully`() {
        val player = playerFactory.createRandomPlayer()

        assertEquals(player, players.get(player.id))
    }

    @Test
    fun `get() throws exception for non existing player`() {
        assertThrows<NoSuchElementException> {
            players.get(1)
        }
    }
}
