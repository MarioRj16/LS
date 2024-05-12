package pt.isel.ls.data.players

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.data.DataMemTests
import pt.isel.ls.utils.Email
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlayerMemTests : DataMemTests(), PlayersTests {
    @Test
    override fun createCreatesPlayerSuccessfully() {
        val name = "testName"
        val email = Email("test@email.com")
        val playerCreate = PlayerCreate(name, email)
        val player = players.create(playerCreate)

        assertEquals(name, player.name)
        assertEquals(email, player.email)
        assertNotNull(player.token)
        assertTrue(player.id > 0)
    }

    @Test
    override fun getReturnsPlayerSuccessfully() {
        val player = playerFactory.createRandomPlayer()

        assertEquals(player, players.get(player.id))
    }

    @Test
    override fun getReturnsNullForNonExistingPlayer() {
        assertNull(players.get(1))
    }

    @Test
    override fun searchReturnsPlayersSuccessfully() {
        val player1 = playerFactory.createRandomPlayer()
        val player2 = playerFactory.createRandomPlayer()
        val player3 = playerFactory.createRandomPlayer()

        val players = players.search(PlayerSearch(null), DEFAULT_SKIP, DEFAULT_LIMIT)

        assertEquals(3, players.size)
        assertTrue(players.contains(player1))
        assertTrue(players.contains(player2))
        assertTrue(players.contains(player3))
    }

    @Test
    override fun searchByNameReturnsPlayersSuccessfully() {
        val player = playerFactory.createRandomPlayer()

        val searchResult = players.search(PlayerSearch(player.name), DEFAULT_SKIP, DEFAULT_LIMIT)

        assertEquals(1, searchResult.size)
        assertEquals(player, searchResult[0])
    }

    @Test
    override fun searchByPartialNameReturnsPlayersSuccessfully() {
        val player = playerFactory.createRandomPlayer()

        val searchResult = players.search(PlayerSearch(player.name.substring(0, 3)), DEFAULT_SKIP, DEFAULT_LIMIT)

        assertEquals(1, searchResult.size)
        assertEquals(player, searchResult[0])
    }
}