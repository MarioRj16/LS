import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.API

class RoutesSite(api: API) {
    private


    private val playerRoutes =
            routes(
                    "player" bind Method.POST to api.playerAPI::createPlayer,
                    "player/{playerId}" bind Method.GET to api.playerAPI::getPlayer,
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
            )
    val app =
            routes(
                    playerRoutes,
                    gameRoutes,
                    sessionRoutes,
            )
}