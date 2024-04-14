package pt.isel.ls.data

import org.junit.jupiter.api.Assertions.assertEquals
import pt.isel.ls.data.mem.GamesMem
import pt.isel.ls.domain.Genre
import kotlin.test.Test

class GenresTest {
    @Test
    fun `test getAllGenres`() {
        val gamesMem = GamesMem()
        val expectedGenres = setOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "RPG"),
            Genre(4, "Simulation"),
            Genre(5, "Strategy"),
        )
        val actualGenres = gamesMem.getAllGenres()
        assertEquals(expectedGenres, actualGenres.values.toSet())
    }
}
