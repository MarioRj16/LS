package pt.isel.ls.integration

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import pt.isel.ls.Routes
import pt.isel.ls.TEST_PORT
import pt.isel.ls.api.API
import pt.isel.ls.api.models.players.PlayerResponse
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Session
import pt.isel.ls.services.Services
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString

abstract class IntegrationTests {
    companion object {
        const val URI_PREFIX = "http://localhost:$TEST_PORT"
        val client = JavaHttpClient()
        var user: PlayerResponse? = null
        val db = DataMem()
        var api = API(Services(db))

        private var jettyServer = Routes(api).app.asServer(Jetty(TEST_PORT))

        @AfterEach
        fun setUp() {
            db.reset()
        }

        @JvmStatic
        @BeforeAll
        fun setup() {
            jettyServer.start()
        }

        @JvmStatic
        @AfterAll
        fun stop() {
            jettyServer.stop()
        }

        inline fun <reified T> Request.json(body: T): Request {
            return this
                .header("content-type", "application/json")
                .body(Json.encodeToString(body))
        }

        inline fun <reified T> Request.token(body: T): Request {
            return this
                .header("Authorization", "Bearer $body")
        }

        @JvmStatic
        @BeforeAll
        fun createUser() {
            val username = generateRandomString()
            val email = generateRandomEmail().email
            val requestBody = mapOf("name" to username, "email" to email)
            val request =
                Request(Method.POST, "$URI_PREFIX/players")
                    .json(requestBody)
            client(request)
                .apply {
                    user = Json.decodeFromString<PlayerResponse>(bodyString())
                }
        }

        fun searchHelpGame(
            repetitions: Int,
            entity: () -> Game,
        ): List<Game> {
            val list = mutableListOf<Game>()
            repeat(repetitions) {
                list.add(entity())
            }
            return list
        }

        fun searchHelpSessions(
            repetitions: Int,
            entity: (Int, Int) -> Session,
            game: Int,
            owner: Int,
        ): List<Session> {
            val list = mutableListOf<Session>()
            repeat(repetitions) {
                list.add(entity(game, owner))
            }
            return list
        }
    }
}
