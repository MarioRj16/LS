package pt.isel.ls

import org.http4k.core.Method
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.singlePageApp
import pt.isel.ls.api.API

class Routes(api: API) {
    private val playerRoutes =
        routes(
            "players" bind Method.GET to api.playerAPI::searchPlayers,
            "players" bind Method.POST to api.playerAPI::createPlayer,
            "players/{playerId}" bind Method.GET to api.playerAPI::getPlayer,
        )
    private val gameRoutes =
        routes(
            "games" bind Method.GET to api.gamesAPI::searchGames,
            "games" bind Method.POST to api.gamesAPI::createGame,
            "games/{gameId}" bind Method.GET to api.gamesAPI::getGame,

        )
    private val sessionRoutes =
        routes(
            "sessions" bind Method.GET to api.sessionsAPI::searchSessions,
            "sessions" bind Method.POST to api.sessionsAPI::createSession,
            "sessions/{sessionId}" bind Method.GET to api.sessionsAPI::getSession,
            "sessions/{sessionId}" bind Method.POST to api.sessionsAPI::addPlayerToSession,
            "sessions/{sessionId}" bind Method.PUT to api.sessionsAPI::updateSession,
            "sessions/{sessionId}" bind Method.DELETE to api.sessionsAPI::deleteSession,
            "sessions/{sessionId}/players/{playerId}" bind Method.DELETE to api.sessionsAPI::removePlayerFromSession,
        )

    private val genresRoutes =
        routes(
            "genres" bind Method.GET to api.genresAPI::getGenres,
        )

    val app =
        routes(
            playerRoutes,
            gameRoutes,
            sessionRoutes,
            genresRoutes,
            singlePageApp(
                ResourceLoader.Directory("static-content"),
            ),
        )
}
