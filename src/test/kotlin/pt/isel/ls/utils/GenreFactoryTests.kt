package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import pt.isel.ls.data.mem.GenresMem
import pt.isel.ls.utils.factories.GenresFactory
import kotlin.test.assertTrue

class GenreFactoryTests {
    @Test
    fun `test generate random genres`() {
        val generatedGenres = GenresFactory().generateRandomGenres()
        val genres = GenresMem().getAllGenres()
        assertTrue(generatedGenres.all { it in genres })
    }
}
