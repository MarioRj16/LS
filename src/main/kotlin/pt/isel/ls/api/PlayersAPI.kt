package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.api.models.PlayerResponse
import pt.isel.ls.services.PlayerServices

class PlayersAPI(private val services: PlayerServices) : APISchema() {
    fun createPlayer(request: Request): Response =
        useWithException {
            val player = services.createPlayer(request.bodyString())
            Response(Status.CREATED)
                .json(PlayerResponse(player.token, player.id))
        }

    fun getPlayer(request: Request): Response =
        useWithException {
            Response(Status.OK)
                .json(
                    services.getPlayer(
                        request.path("playerId")?.toInt(),
                        request.header("Authorization"),
                    ),
                )
        }
}
