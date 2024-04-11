package pt.isel.ls.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.utils.generateRandomString
import pt.isel.ls.utils.generateSetOfRandomInts

class GamesModelsTests {
    @Test
    fun `GameCreate can be created`() {
        val name = generateRandomString()
        val developer = generateRandomString()
        val genres = generateSetOfRandomInts(1)

        val gameCreate = GameCreate(name, developer, genres)

        assert(gameCreate.name == name)
        assert(gameCreate.developer == developer)
        assert(gameCreate.genres == genres)
    }

    @Test
    fun `GameCreate cannot be created with blank name`() {
        val name = ""
        val developer = "developer"
        val genres = generateSetOfRandomInts(1)

        assertThrows<IllegalArgumentException> {
            GameCreate(name, developer, genres)
        }
    }

    @Test
    fun `GameCreate cannot be created with blank developer`() {
        val name = "name"
        val developer = ""
        val genres = generateSetOfRandomInts(1)

        assertThrows<IllegalArgumentException> {
            GameCreate(name, developer, genres)
        }
    }

    @Test
    fun `GameCreate cannot be created with empty genres`() {
        val name = "name"
        val developer = "developer"
        val genres = setOf<Int>()

        assertThrows<IllegalArgumentException> {
            GameCreate(name, developer, genres)
        }
    }
}
