package pt.isel.ls.api

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameResponse
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.services.GamesServices

class GamesAPI(private val services: GamesServices) : APISchema() {
    fun searchGames(request: Request): Response =
        useWithException {
            logRequest(request)
            val searchParameters = Json.decodeFromString<GameSearch>(request.bodyString())
            Response(Status.OK)
                .json(
                    services.searchGames(
                        searchParameters,
                        request.header("Authorization"),
                        request.query("skip")?.toInt(),
                        request.query("limit")?.toInt(),
                    ),
                )
        }

    fun createGame(request: Request): Response =
        useWithException {
            logRequest(request)
            val gameInput = Json.decodeFromString<GameCreate>(request.bodyString())
            val gameId =
                services.createGame(
                    gameInput,
                    request.header("Authorization"),
                )
            Response(Status.CREATED)
                .json(GameResponse(gameId))
        }

    fun getGame(request: Request): Response =
        useWithException {
            logRequest(request)
            Response(Status.OK)
                .json(
                    services.getGame(
                        request.path("gameId")?.toInt(),
                        request.header("Authorization"),
                    ),
                )
        }
}
