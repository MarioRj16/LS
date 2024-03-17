package pt.isel.ls.data

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.AppFactory
import kotlin.test.assertEquals

class PlayerTests: DataMem() {

    private companion object: AppFactory(DataMem())

    @BeforeEach
    fun beforeEachTest() = reset()

    @Test
    fun `Player IDs are correctly assigned`(){
        val player = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()

        assertEquals(1, player.id)
        assertEquals(2, player2.id)
    }

    @Test
    fun `create() does not throw exception with unique valid email`(){
        assertDoesNotThrow {
            players.create("name", "valid@email.com")
        }
    }

    @Test
    fun `create() throws exception if email is not unique`(){
        players.create("name", "email@email.com")

        assertThrows<IllegalArgumentException> {
            players.create("name2", "email@email.com")
        }
    }

    @Test
    fun `create() throws exception if email is not valid`(){
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
    fun `get() throws if player does not exist`(){
        assertThrows<NoSuchElementException> {
            players.get(1)
        }
    }

    @Test
    fun `get() returns the right player`(){
        val player = playerFactory.createRandomPlayer()

        assertEquals(players.get(1), player)
    }
}