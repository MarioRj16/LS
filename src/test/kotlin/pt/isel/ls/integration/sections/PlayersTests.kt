
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.api.models.PlayerResponse
import pt.isel.ls.domain.Player
import pt.isel.ls.integration.IntegrationTests
import kotlin.test.assertEquals

class PlayersTests : IntegrationTests() {

    @Test
    fun createPlayer()  {
        val requestBody = PlayerCreate("diferente", "diferente@gmail.com")
        val request =
            Request(Method.POST, "$URI_PREFIX/player")
                .json(requestBody)
        client(request)
            .apply {
                assertEquals(Status.CREATED, status)
                assertDoesNotThrow { Json.decodeFromString<PlayerResponse>(bodyString()).playerId }
            }
    }

    @Test
    fun getPlayer()  {
        val request =
            Request(Method.GET, "$URI_PREFIX/player/${user!!.playerId}")
                .json("")
                .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK, status)
                assertEquals(Json.decodeFromString<Player>(bodyString()).id, user!!.playerId)
            }
    }
}
