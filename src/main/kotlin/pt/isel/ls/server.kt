package pt.isel.ls

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.api.API
import pt.isel.ls.data.postgres.DataPostgres
import pt.isel.ls.services.Services

private val logger = LoggerFactory.getLogger("pt.isel.ls")

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
    val db = DataPostgres(System.getenv("JDBC_conn"))
   // db.create()
    //val db=DataMem()
    val api = API(Services(db))
    val playerRoutes =
        routes(
            "player" bind POST to api.playerAPI::createPlayer,
            "player/{playerId}" bind GET to api.playerAPI::getPlayer
        )
    val gameRoutes=
        routes(
            "games" bind GET to api.gamesAPI::searchGames,
            "games" bind POST to api.gamesAPI::createGame,
            "games/{gameId}" bind GET to api.gamesAPI::getGame
        )
    val sessionRoutes=
        routes(
            "sessions" bind GET to api.sessionsAPI::searchSessions,
            "sessions" bind POST to api.sessionsAPI::createSession,
            "sessions/{sessionId}" bind GET to api.sessionsAPI::getSession,
            "sessions/{sessionId}" bind POST to api.sessionsAPI::addPlayerToSession
        )
    val app =
        routes(
            playerRoutes,
            gameRoutes,
            sessionRoutes
        )
    val jettyServer = app.asServer(Jetty(PORT)).start()
    logger.info("server started listening")
    readln()
    jettyServer.stop()

    logger.info("leaving Main")


}