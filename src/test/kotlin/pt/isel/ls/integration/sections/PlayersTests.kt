package pt.isel.ls.integration.sections

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.api.models.players.PlayerListResponse
import pt.isel.ls.api.models.players.PlayerResponse
import pt.isel.ls.integration.IntegrationTests
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlayersTests : IntegrationTests() {

    @BeforeEach
    fun setUp() {
        db.reset()
    }

    @Test
    fun `searchPlayer returns 200 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val request = Request(Method.GET, "$URI_PREFIX/players")
            .json("")
            .token(player.token)

        client(request)
            .apply {
                val res = Json.decodeFromString<PlayerListResponse>(bodyString())
                assertEquals(Status.OK, status)
                assertEquals(1, res.total)
                assertTrue { res.players.any { it.id == player.id } }
            }
    }

    @Test
    fun `createPlayer returns 201 for good request`() {
        val requestBody = PlayerCreate.create()
        val request =
            Request(Method.POST, "$URI_PREFIX/players")
                .json(requestBody)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<PlayerResponse>(bodyString()) }
            }
    }

    @Test
    fun `createPlayer returns 400 missing name`() {
        val requestBody = PlayerCreate.create(name = "")
        val request =
            Request(Method.POST, "$URI_PREFIX/players")
                .json(requestBody)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createPlayer returns 400 for non-unique name`() {
        val player = playerFactory.createRandomPlayer()
        val requestBody = PlayerCreate.create(name = player.name)
        val request =
            Request(Method.POST, "$URI_PREFIX/players")
                .json(requestBody)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `createPlayer returns 400 for non-unique email`() {
        val player = playerFactory.createRandomPlayer()
        val requestBody = PlayerCreate.create(email = player.email)
        val request =
            Request(Method.POST, "$URI_PREFIX/players")
                .json(requestBody)
        client(request)
            .apply {
                assertEquals(Status.BAD_REQUEST, status)
            }
    }

    @Test
    fun `getPlayer returns 200 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val request =
            Request(Method.GET, "$URI_PREFIX/players/${player.id}")
                .json("")
                .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                assertEquals(Json.decodeFromString<PlayerDetails>(bodyString()).id, player.id)
            }
    }

    @Test
    fun `getPlayer returns 404 for non-existing player`() {
        val player = playerFactory.createRandomPlayer()
        val request =
            Request(Method.GET, "$URI_PREFIX/players/9999")
                .json("")
                .token(player.token)
        client(request)
            .apply {
                assertEquals(Status.NOT_FOUND, status)
            }
    }
}
