package pt.isel.ls.models

import org.junit.jupiter.api.Test
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
}
