package pt.isel.ls.integration

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import pt.isel.ls.TEST_PORT
import pt.isel.ls.api.API
import pt.isel.ls.api.models.PlayerResponse
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.services.Services
import pt.isel.ls.utils.generateRandomEmail


abstract class IntegrationTests (){
    companion object {
        const val URI_PREFIX = "http://localhost:$TEST_PORT"
        val client = JavaHttpClient()
        var user : PlayerResponse? = null
        val db = DataMem()
        var api = API(Services(db))
        val playerRoutes =
            routes(
                "player" bind Method.POST to api.playerAPI::createPlayer,
                "player/{playerId}" bind Method.GET to api.playerAPI::getPlayer
            )
        val gameRoutes =
            routes(
                "games" bind Method.GET to api.gamesAPI::searchGames,
                "games" bind Method.POST to api.gamesAPI::createGame,
                "games/{gameId}" bind Method.GET to api.gamesAPI::getGame
            )
        val sessionRoutes =
            routes(
                "sessions" bind Method.GET to api.sessionsAPI::searchSessions,
                "sessions" bind Method.POST to api.sessionsAPI::createSession,
                "sessions/{sessionId}" bind Method.GET to api.sessionsAPI::getSession,
                "sessions/{sessionId}" bind Method.POST to api.sessionsAPI::addPlayerToSession
            )
        val app =
            routes(
                playerRoutes,
                gameRoutes,
                sessionRoutes
            )

        var jettyServer = app.asServer(Jetty(TEST_PORT))

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
                .header("Authorization","Bearer $body")
        }


        @JvmStatic
        @BeforeAll
        fun createUser(): Unit {
            val requestBody = mapOf("name" to "user", "email" to generateRandomEmail())
            val request = Request(Method.POST, "$URI_PREFIX/player")
                .json(requestBody)
            client(request)
                .apply {
                    user = Json.decodeFromString<PlayerResponse>(bodyString())
                }
        }

        fun searchHelpGame(repetitions:Int, entity: () -> Game):List<Game>{
            val list = mutableListOf<Game>()
            repeat (repetitions) {
                list.add(entity())
            }
            return list
        }

        fun searchHelpSessions(repetitions:Int, entity: (Int) -> GamingSession, game:Int ):List<GamingSession>{
            val list = mutableListOf<GamingSession>()
            repeat (repetitions) {
                list.add(entity(game))
            }
            return list
        }


    }
}