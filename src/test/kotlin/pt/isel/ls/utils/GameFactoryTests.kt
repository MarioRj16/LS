package pt.isel.ls.utils

import pt.isel.ls.data.mem.GamesMem
import pt.isel.ls.utils.factories.GameFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class GameFactoryTests {
    @Test
    fun `createRandomGame() creates game successfully`() {

        val games = GamesMem()
        val gameFactory = GameFactory(games)
        val game = gameFactory.createRandomGame()

        assertEquals(game, games.get(game.name))
    }
}