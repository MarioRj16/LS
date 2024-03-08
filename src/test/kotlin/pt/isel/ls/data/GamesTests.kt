package pt.isel.ls.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.Data.Mem.DataMem
import pt.isel.ls.Data.Mem.DataMemSchema
import pt.isel.ls.Domain.Genre
import kotlin.test.assertEquals

class GamesTests {
    val db = DataMem(DataMemSchema())

    @Test
    fun `Game can be created`(){
        val game = db.games.create(
            name = "name",
            developer = "developer",
            genres = setOf(Genre("genre"))
        )

        assertEquals(game, db.games.get("name"))

    }

    @Test
    fun `Integrity Restrictions are enforced`(){
        db.games.create(name = "name", developer = "developer", genres = setOf(Genre("genre")))

        assertThrows<IllegalArgumentException> {
            db.games.create(name = "name", developer = "developer2", genres = setOf(Genre("genre2")))
        }

        assertThrows<IllegalArgumentException> {
            db.games.create(name = "name2", developer = "developer2", genres = setOf())
        }

    }
}