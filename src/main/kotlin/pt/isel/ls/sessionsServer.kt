package pt.isel.ls

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.api.GamesAPI
import pt.isel.ls.api.PlayersAPI
import pt.isel.ls.api.SessionsAPI
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.services.GamesServices
import pt.isel.ls.services.PlayerServices
import pt.isel.ls.services.SessionServices

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
    val db = DataMem()
    val player = PlayersAPI(PlayerServices(db))
    val games = GamesAPI(GamesServices(db))
    val session = SessionsAPI(SessionServices(db))
    /*
    val yamlFile = File("API-docs 1.0.yaml")
    val yamlContent = yamlFile.readText()
    val docsAPI=Yaml.decodeFromString

     */
    val playerRoutes =
        routes(
            "player" bind POST to player::createPlayer,
            "player/{playerId}" bind GET to player::getPlayer
        )
    val gameRoutes=
        routes(
            "games" bind GET to games::searchGames,
            "games" bind POST to games::createGame,
            "games/{gameId}" bind GET to games::getGame
        )
    val sessionRoutes=
        routes(
            "sessions" bind GET to session::searchSessions,
            "sessions" bind POST to session::createSession,
            "sessions/{sessionId}" bind GET to session::getSession,
            "sessions/{sessionId}" bind POST to session::addPlayerToSession
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