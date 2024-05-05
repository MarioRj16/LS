package pt.isel.ls.integration.sections
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.api.models.players.PlayerListResponse
import pt.isel.ls.api.models.players.PlayerResponse
import pt.isel.ls.integration.IntegrationTests
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlayersTests : IntegrationTests() {

    @Test
    fun `searchPlayer returns 200 for good request`() {
        val request = Request(Method.GET, "$URI_PREFIX/players")
            .json("")
            .token(user!!.token)

        client(request)
            .apply {
                val res = Json.decodeFromString<PlayerListResponse>(bodyString())
                assertEquals(Status.OK, status)
                assertTrue { res.players.any { it.id == user!!.playerId } }
            }
    }

    @Test
    fun `createPlayer returns 201 for good request`() {
        val requestBody = PlayerCreate(generateRandomString(), generateRandomEmail())
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
        val requestBody = PlayerCreate("", generateRandomEmail())
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
        val requestBody = PlayerCreate(player.name, generateRandomEmail())
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
        val requestBody = PlayerCreate(generateRandomString(), player.email)
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
        val request =
            Request(Method.GET, "$URI_PREFIX/players/${user!!.playerId}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                assertEquals(Json.decodeFromString<PlayerDetails>(bodyString()).id, user!!.playerId)
            }
    }

    @Test
    fun `getPlayer returns 404 for non-existing player`() {
        val request =
            Request(Method.GET, "$URI_PREFIX/players/9999")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.NOT_FOUND, status)
            }
    }
}
