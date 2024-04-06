package pt.isel.ls.api

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameResponse
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.services.GamesServices
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.isPositive
import pt.isel.ls.utils.validateInt

class GamesAPI(private val services: GamesServices) : APISchema() {
    fun searchGames(request: Request): Response =
        request.useWithException { token ->
            val searchParameters = Json.decodeFromString<GameSearch>(request.bodyString())
            val skip = request.query("skip")?.toInt().validateInt(DEFAULT_SKIP) { it.isNotNegative() }
            val limit = request.query("limit")?.toInt().validateInt(DEFAULT_LIMIT) { it.isNotNegative() }
            Response(Status.OK)
                .json(
                    services.searchGames(searchParameters, token, skip, limit),
                )
        }

    fun createGame(request: Request): Response =
        request.useWithException { token ->
            val gameInput = Json.decodeFromString<GameCreate>(request.bodyString())
            val gameId = services.createGame(gameInput, token)
            Response(Status.CREATED)
                .json(GameResponse(gameId))
        }

    fun getGame(request: Request): Response =
        request.useWithException { token ->
            val gameId = request.path("gameId")?.toInt().validateInt { it.isPositive() }
            Response(Status.OK)
                .json(services.getGame(gameId, token))
        }
}
