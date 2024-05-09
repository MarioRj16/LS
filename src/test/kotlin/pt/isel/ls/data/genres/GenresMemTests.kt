package pt.isel.ls.data.genres

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pt.isel.ls.data.DataMemTests
import pt.isel.ls.data.mem.GenresMem
import pt.isel.ls.domain.Genre


class GenresMemTests: DataMemTests(), GenresTests {
    @Test
    override fun `test getAllGenres`() {
        val gamesMem = GenresMem()
        val expectedGenres = setOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "RPG"),
            Genre(4, "Simulation"),
            Genre(5, "Strategy"),
        )
        val actualGenres = gamesMem.getAllGenres()
        assertEquals(expectedGenres, actualGenres)
    }
}
