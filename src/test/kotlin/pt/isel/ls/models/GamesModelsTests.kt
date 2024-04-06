package pt.isel.ls.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.generateRandomString

class GamesModelsTests {
    @Test
    fun `GameCreate can be created`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genres = setOf(Genre(1, "action"))

        val gameCreate = GameCreate(name, developer, genres)

        assert(gameCreate.name == name)
        assert(gameCreate.developer == developer)
        assert(gameCreate.genres == genres)
    }

    @Test
    fun `GameCreate cannot be created with blank name`() {
        val name = ""
        val developer = "developer"
        val genres = setOf(Genre(1, "action"))

        assertThrows<IllegalArgumentException> {
            GameCreate(name, developer, genres)
        }
    }

    @Test
    fun `GameCreate cannot be created with blank developer`() {
        val name = "name"
        val developer = ""
        val genres = setOf(Genre(1, "action"))

        assertThrows<IllegalArgumentException> {
            GameCreate(name, developer, genres)
        }
    }

    @Test
    fun `GameCreate cannot be created with empty genres`() {
        val name = "name"
        val developer = "developer"
        val genres = setOf<Genre>()

        assertThrows<IllegalArgumentException> {
            GameCreate(name, developer, genres)
        }
    }
}
