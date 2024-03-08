package pt.isel.ls.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.Data.Mem.DataMem
import pt.isel.ls.Data.Mem.DataMemSchema
import kotlin.test.assertEquals

class PlayerTests {
    val db = DataMem(DataMemSchema())
    @Test
    fun `User can be created`(){
        val player = db.players.create("name", "email@email.com")
        assertEquals(db.players.search(1), player)
    }

    @Test
    fun `Integrity Restrictions are enforced`(){
        val player1 = db.players.create("name", "email2@email.com")
        assertThrows<IllegalArgumentException> {
            db.players.create("name", "email2@email.com")
        }

        assertThrows<IllegalArgumentException> {
            db.players.create("name", "email.com")
        }
    }
}