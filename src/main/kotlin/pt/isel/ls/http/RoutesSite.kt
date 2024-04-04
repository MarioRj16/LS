import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.API

class RoutesSite(api: API) {

    private val playerRoutes =
            routes(
                    "playerHome" bind Method.GET to api,
                    "player/{playerId}" bind Method.GET to api,
                    "player/{playerId}" bind Method.POST to api
            )
    private val gameRoutes =
            routes(
                    "gamesSearch" bind Method.GET to api,
                    "gamesSearch" bind Method.POST to api,
                    "games" bind Method.GET to api,
                    "games" bind Method.POST to api,
                    "games/{gameId}" bind Method.GET to api,
                    "games/{gameId}" bind Method.POST to api
            )
    private val gamingSessionRoutes =
            routes(
                    "gamingSessionsSearch" bind Method.GET to api,
                    "gamingSessionsSearch" bind Method.POST to api,
                    "gamingSessions" bind Method.GET to api,
                    "gamingSessions" bind Method.POST to api,
                    "gamingSessions/{gamingSessionId}" bind Method.GET to api,
                    "gamingSessions/{gamingSessionId}" bind Method.POST to api,
            )
    val app =
            routes(
                    playerRoutes,
                    gameRoutes,
                    gamingSessionRoutes,
            )
}