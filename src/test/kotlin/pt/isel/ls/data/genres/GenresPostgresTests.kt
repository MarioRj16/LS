package pt.isel.ls.data.genres

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pt.isel.ls.data.DataPostgresTests

class GenresPostgresTests : DataPostgresTests(), GenresTests {
    @Test
    override fun testGetAllGenres() {
        val actualGenres = genres.getAllGenres()
        assertEquals(actualGenres.size, 96)
    }
}
