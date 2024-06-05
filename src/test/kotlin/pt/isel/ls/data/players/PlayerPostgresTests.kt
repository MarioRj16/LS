package pt.isel.ls.data.players

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.data.DataPostgresTests
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlayerPostgresTests : DataPostgresTests(), PlayersTests {
    @Test
    override fun createCreatesPlayerSuccessfully() {
        val playerCreate = PlayerCreate.create()
        val player = players.create(playerCreate)

        assertEquals(playerCreate.name, player.name)
        assertEquals(playerCreate.email, player.email)
        assertTrue(playerCreate.password.verify(player.password))
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
        val searchResults = players.search(PlayerSearch(null), DEFAULT_SKIP, DEFAULT_LIMIT)
        assertEquals(setOf(player1.id, player2.id, player3.id), searchResults.element.map { it.id }.toSet())
    }

    @Test
    override fun searchByNameReturnsPlayersSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val searchResult = players.search(PlayerSearch(player.name), DEFAULT_SKIP, DEFAULT_LIMIT)
        assertEquals(setOf(player.id), searchResult.element.map { it.id }.toSet())
    }

    @Test
    override fun searchByPartialNameReturnsPlayersSuccessfully() {
        val player = playerFactory.createRandomPlayer()
        val searchResult = players.search(PlayerSearch(player.name.substring(0, 3)), DEFAULT_SKIP, DEFAULT_LIMIT)
        assertEquals(setOf(player.id), searchResult.element.map { it.id }.toSet())
    }
}
