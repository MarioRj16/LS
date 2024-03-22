package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import pt.isel.ls.data.mem.PlayersMem
import pt.isel.ls.utils.factories.PlayerFactory
import kotlin.test.assertEquals

class PlayerFactoryTests {

    @Test
    fun `createRandomPlayer() creates player successfully`(){
        val players = PlayersMem()
        val playerFactory = PlayerFactory(players)
        val player = playerFactory.createRandomPlayer()

        assertEquals(player, players.get(player.id))
    }
}