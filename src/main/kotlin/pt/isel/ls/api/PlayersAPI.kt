package pt.isel.ls.api

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerResponse
import pt.isel.ls.services.PlayerServices
import pt.isel.ls.utils.isPositive
import pt.isel.ls.utils.validateInt

class PlayersAPI(val services: PlayerServices) : APISchema() {
    fun createPlayer(request: Request): Response =
        request.useWithExceptionNoToken {
            val input = Json.decodeFromString<PlayerCreate>(request.bodyString())
            // TODO: Player information should not be sent in the request body
            val player = services.createPlayer(input)
            val responseBody = PlayerResponse(player.token, player.id)
            Response(Status.CREATED)
                .json(responseBody)
        }

    fun getPlayer(request: Request): Response =
        request.useWithException { token ->
            val playerId = request.path("playerId")?.toInt().validateInt { it.isPositive() }
            Response(Status.OK)
                .json(
                    services.getPlayer(playerId, token),
                )
        }
}
