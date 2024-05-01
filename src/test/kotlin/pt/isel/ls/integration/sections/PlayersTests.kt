
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
import kotlin.test.assertEquals

class PlayersTests : IntegrationTests() {

    @Test
    fun searchPlayer() {
        val request = Request(Method.GET, "$URI_PREFIX/players")
            .json("")
            .token(user!!.token)

        client(request)
            .apply {
                val res = Json.decodeFromString<PlayerListResponse>(bodyString())
                assertEquals(Status.OK, status)
                assertEquals(1, res.total)
            }
    }

    @Test
    fun createPlayer() {
        val requestBody = PlayerCreate("diferente", Email("diferente@gmail.com"))
        val request =
            Request(Method.POST, "$URI_PREFIX/players")
                .json(requestBody)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<PlayerResponse>(bodyString()).playerId }
            }
    }

    @Test
    fun getPlayer() {
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

}
