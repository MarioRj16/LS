package pt.isel.ls.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.Data.Mem.DataMem
import pt.isel.ls.Domain.Genre
import kotlin.test.assertEquals

class GamesTests: DataMem() {

    @Test
    fun `Game can be created`(){
        val game = games.create(
            name = "name",
            developer = "developer",
            genres = setOf(Genre("genre"))
        )

        //assertEquals(game, games.get("name"))
        assertEquals(game, games.get(1))
    }

    @Test
    fun `Integrity Restrictions are enforced`(){
        games.create(name = "name", developer = "developer", genres = setOf(Genre("genre")))

        assertThrows<IllegalArgumentException> {
            games.create(name = "name", developer = "developer2", genres = setOf(Genre("genre2")))
        }

        assertThrows<IllegalArgumentException> {
            games.create(name = "name2", developer = "developer2", genres = setOf())
        }

    }
}