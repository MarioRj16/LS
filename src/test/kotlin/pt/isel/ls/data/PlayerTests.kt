package pt.isel.ls.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import kotlin.test.assertEquals

class PlayerTests: DataMem() {
    @Test
    fun `User can be created`(){
        val player = players.create("name", "email@email.com")
        val expected = players.get(1)
        assertEquals(expected, player)
    }

    @Test
    fun `Integrity Restrictions are enforced`(){
        players.create("name", "email2@email.com")

        assertThrows<IllegalArgumentException> {
            players.create("name", "email2@email.com")
        }

        assertThrows<IllegalArgumentException> {
            players.create("name", "email.com")
        }
    }
}