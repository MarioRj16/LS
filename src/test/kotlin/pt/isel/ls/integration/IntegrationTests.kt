package pt.isel.ls.integration

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.client.JavaHttpClient
import org.http4k.core.Request
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import pt.isel.ls.Routes
import pt.isel.ls.TEST_PORT
import pt.isel.ls.api.API
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.services.Services
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.GenresFactory
import pt.isel.ls.utils.factories.PlayerFactory

abstract class IntegrationTests {
    companion object {
        const val URI_PREFIX = "http://localhost:$TEST_PORT"
        val client = JavaHttpClient()
        val db = DataMem()
        //val db = DataPostgres(System.getenv(CONN_NAME))
        var api = API(Services(db))
        val genresFactory = GenresFactory(db.genres)
        val playerFactory = PlayerFactory(db.players)
        val gameFactory = GameFactory(db.games, db.genres)
        val sessionFactory = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)


        private var jettyServer = Routes(api).app.asServer(Jetty(TEST_PORT))

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
    }
}
