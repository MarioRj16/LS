package pt.isel.ls.api

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.services.PlayersServices
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.isPositive
import pt.isel.ls.utils.validateInt

class PlayersAPI(val services: PlayersServices) : APISchema() {
    fun createPlayer(request: Request): Response =
        request.useWithExceptionNoToken {
            val input = Json.decodeFromString<PlayerCreate>(request.bodyString())
            Response(Status.CREATED)
                .json(services.createPlayer(input))
        }

    fun getPlayer(request: Request): Response =
        request.useWithException { token ->
            val playerId = request.path("playerId")?.toInt().validateInt { it.isPositive() }
            Response(Status.OK)
                .json(
                    services.getPlayer(playerId, token),
                )
        }

    fun searchPlayers(request: Request): Response =
        request.useWithException { token ->
            val username = request.query("username")
            val skip = request.query("skip")?.toInt().validateInt(DEFAULT_SKIP) { it.isNotNegative() }
            val limit = request.query("limit")?.toInt().validateInt(DEFAULT_LIMIT) { it.isNotNegative() }
            val searchParams = PlayerSearch(username)
            Response(Status.OK).json(services.searchPlayers(searchParams, token, skip, limit))
        }
}
