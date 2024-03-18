package pt.isel.ls.data

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.AppFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlayerTests: DataMem() {

    private companion object: AppFactory(DataMem())

    @BeforeEach
    fun setUp() = reset()

    @Test
    fun `create() creates player successfully`(){
        val name = "testName"
        val email = "test@email.com"
        val player = players.create(name, email)

        assertEquals(name, player.name)
        assertEquals(email, player.email)
        assertNotNull(player.token)
        assertTrue(player.id == 1)
    }

    @Test
    fun `create() throws exception for non unique email`(){
        val email = "email@email.com"
        players.create("name", email)
        assertThrows<IllegalArgumentException> {
            players.create("name2", email)
        }
    }

    @Test
    fun `create() throws exception for email in invalid format`(){
        assertThrows<IllegalArgumentException> {
            players.create("name", "invalidEmail")
        }

        assertThrows<IllegalArgumentException> {
            players.create("name2", "invalid@email")
        }

        assertThrows<IllegalArgumentException> {
            players.create("name3", "invalid@email.")
        }
    }

    @Test
    fun `get() returns player successfully`(){
        val player = playerFactory.createRandomPlayer()

        assertEquals(player, players.get(player.id))
    }

    @Test
    fun `get() throws exception for non existing player`(){
        assertThrows<NoSuchElementException> {
            players.get(1)
        }
    }
}