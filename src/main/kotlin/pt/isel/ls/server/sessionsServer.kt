package pt.isel.ls.server

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.Data.Storage
import pt.isel.ls.server.API.*

private val logger = LoggerFactory.getLogger("pt.isel.ls.server")

/**
fun getDate(request: Request): Response {
    return Response(OK)
        .header("content-type", "text/plain")
        .body(Clock.System.now().toString())
}

*/
fun logRequest(request: Request) {
    logger.info(
        "incoming request: method={}, uri={}, content-type={} accept={}",
        request.method,
        request.uri,
        request.header("content-type"),
        request.header("accept"),
    )
}

fun main() {
    //TODO could add more routes
    //TODO add swagger with .yaml file
  //  val data=
    val playerRoutes =
        routes(
            "player" bind POST to ::createPlayer,
            "player/{playerId}" bind GET to ::getPlayer
        )
    val gameRoutes=
        routes(
            "games" bind GET to ::searchGames,
            "games" bind POST to ::createGame,
            "games/{gameId}" bind GET to ::getGame
        )
    val sessionRoutes=
        routes(
            "sessions" bind GET to ::getSessions,
            "sessions" bind POST to ::createSession,
            "sessions/{sessionId}" bind GET to ::getSession,
            "sessions/{sessionId}" bind POST to ::addPlayertoSession
        )
    val app =
        routes(
            playerRoutes,
            gameRoutes,
            sessionRoutes
        )
    val jettyServer = app.asServer(Jetty(8000)).start()
    logger.info("server started listening")

    readln()
    jettyServer.stop()

    logger.info("leaving Main")
}